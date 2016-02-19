package com.studentbase.app;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

import com.studentbase.app.resources.AuthentificationResource;
import com.studentbase.app.resources.WeatherResource;

import net.sf.ehcache.CacheManager;

public class Listener implements javax.servlet.ServletContextListener {

	// Logger
	final static Logger LOG = Logger.getLogger(Listener.class);

	// Cache manager instance
	CacheManager cm = null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		// Clear all caches
		cm.clearAll();
		LOG.debug("All caches cleared");

	}
	 
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		// Create new cache
//	    cm = CacheManager.create();  
		cm = CacheManager.newInstance();

		LOG.debug("Local cache created");

	    //AuthentificationResource.setCacheManager(cm);
		//WeatherResource.setCacheManager(cm);
		//AuthentificationFilter.setCache(cm);
	}
}


//distributed cache
/**
 * Scheduler that execute code in interval to monitoring terracotta server
 */
/*
 
     private static final int INITIAL_DELAY = 0;                     //time to delay first execution
    private static final int PERIOD = 2;                            //period between successive executions
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;     //time unit of the initialDelay and period parameters

          
        String teracotaURL = "localhost:9510";
        URL url = find("ehcache.xml");
		 net.sf.ehcache.config.Configuration config = ConfigurationFactory.parseConfiguration(url);
	        TerracottaClientConfiguration tcf = new TerracottaClientConfiguration();
	        
	            tcf.setUrl(teracotaURL);
	            tcf.setRejoin(false);
	            config.addTerracottaConfig(tcf);
	            config.setUpdateCheck(false);


 
 	public URL find(String resourceName) {
	    
	    URL url = this.getClass().getClassLoader().getResource(resourceName);

	    if(url == null) {
	        if( resourceName.startsWith("/")) {
	            resourceName = resourceName.substring(1);
	        }
	        
	        url = this.getClass().getClassLoader().getResource(resourceName);
	    }
	    
	    return url;
	}

  
 private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
 
    public Thread newThread(Runnable r) {
        Thread th = new Thread(r);
        th.setDaemon(true);             //marks this thread to exit when the only threads running are all daemon threads.
        return th;
    }
});

        try {
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
/*	// cache manager
static CacheManager cacheManager;

// cache instance
static Ehcache cache;

// getter and setter of cache manager
public static CacheManager getCacheManager() {
	return cacheManager;
}

public static void setCacheManager(CacheManager cacheManager) {
	cache = cacheManager.getEhcache(TOKEN_CACHE_NAME);
}
*/	
