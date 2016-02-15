package com.studentbase.app;

import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

import com.studentbase.app.resources.AuthentificationResource;
import com.studentbase.app.resources.WeatherResource;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.cluster.ClusterScheme;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.TerracottaClientConfiguration;

public class Listener implements javax.servlet.ServletContextListener {

	// Logger
	final static Logger LOG = Logger.getLogger(Listener.class);

	// Cache manager instance
	CacheManager cm = null;

    private static final int INITIAL_DELAY = 0;                     //time to delay first execution
    private static final int PERIOD = 2;                            //period between successive executions
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;     //time unit of the initialDelay and period parameters

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		// Clear all caches
		cm.clearAll();
		LOG.debug("All caches cleared");

	}

	public URL find(String resourceName) {
	    
	    URL url = this.getClass().getClassLoader().getResource(resourceName);

	    if(url == null) {
	        if( resourceName.startsWith("/")) {
	            resourceName = resourceName.substring(1);
	        }
	        
	        url = this.getClass().getClassLoader().getResource(resourceName);
	    }
	    
	    return url;
//	    return url != null ? url : instance.getClass().getResource("/" + resourceName);
	}
	 
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// Create new cache
		//cm = CacheManager.newInstance();
		
        String teracotaURL = "localhost:9510";
        URL url = find("ehcache.xml");
		 net.sf.ehcache.config.Configuration config = ConfigurationFactory.parseConfiguration(url);
	        TerracottaClientConfiguration tcf = new TerracottaClientConfiguration();
	        
	            tcf.setUrl(teracotaURL);
	            tcf.setRejoin(false);
	            config.addTerracottaConfig(tcf);
	            config.setUpdateCheck(false);

	            cm = CacheManager.create(config);  

	    AuthentificationResource.setCacheManager(cm);
		WeatherResource.setCacheManager(cm);
		AuthentificationFilter.setCache(cm);
        
/*        try {
            //Creates and executes a periodic action
            scheduler.scheduleAtFixedRate(new Runnable() {
            	
            	boolean terracottaRunning = false;
            	
                public void run() {
                	terracottaRunning = cm.getCluster(ClusterScheme.TERRACOTTA).isClusterOnline();
    	            
    	            if(terracottaRunning) {
    	            	LOG.info("SERVER RUNNING");
    	            }
    	            else 
    	            	LOG.info("SERVER STOPPED!");
    	            
                }
            }, INITIAL_DELAY, PERIOD, TIME_UNIT);                           //execute every 2 seconds again
        } catch(Exception e) {
            throw new IllegalArgumentException("Error in scheduler.");
        }
*/
	}
	
    /**
     * Scheduler that execute code in interval to monitoring terracotta server
     */
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        public Thread newThread(Runnable r) {
            Thread th = new Thread(r);
            th.setDaemon(true);             //marks this thread to exit when the only threads running are all daemon threads.
            return th;
        }
    });

}