package com.studentbase.app;

import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.terracotta.toolkit.InvalidToolkitConfigException;
import org.terracotta.toolkit.Toolkit;
import org.terracotta.toolkit.ToolkitFactory;
import org.terracotta.toolkit.ToolkitInstantiationException;

import com.studentbase.app.resources.AuthentificationResource;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.TerracottaClientConfiguration;

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

	 public URL find(String resourceName)
	  {
	    
	    URL url = this.getClass().getClassLoader().getResource(resourceName);

	    if(url == null)
	    {
	        if( resourceName.startsWith("/"))
	        {
	            resourceName = resourceName.substring(1);
	        }
	        
	        url = this.getClass().getClassLoader().getResource(resourceName);
	    }
	    
	    return url;
//	    return url != null ? url : instance.getClass().getResource("/" + resourceName);
	  }
	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		
/*		final Properties properties = new Properties();
        properties.put("rejoin","false");
        final String terracotaToolkitPath = "toolkit:terracotta://" + "localhost:9510";
		 Toolkit toolkit = null;
	             try {
					toolkit = ToolkitFactory.createToolkit(terracotaToolkitPath,properties);
				} catch (InvalidToolkitConfigException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ToolkitInstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
*/		
         
		// Create new cache
		cm = CacheManager.newInstance();

        String teracotaURL = "localhost:9510";
        URL url = find("ehcache.xml");
		 net.sf.ehcache.config.Configuration config = ConfigurationFactory.parseConfiguration(url);
	        TerracottaClientConfiguration tcf = new TerracottaClientConfiguration();
	            tcf.setUrl(teracotaURL);
	            tcf.setRejoin(false);
	            config.addTerracottaConfig(tcf);
	            config.setUpdateCheck(false);
	            cm = CacheManager.create(config);  
		
		LOG.info("Cache created");
		
		AuthentificationResource.setCacheManager(cm);
		AuthentificationFilter.setCache(cm);
	}
}