package com.studentbase.app;

import java.net.URL;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.TerracottaClientConfiguration;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {

	//logger
	final static Logger LOG = Logger.getLogger(AppTest.class);

	static Session session;
		
	@Before
	public void beforeTest() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	@After
	public void afterTest() {
		session.close();
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

	 
	@Test
	public void test() {
		CacheManager cm = null;
		
		//cm.newInstance();
		
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

		Ehcache cache = cm.getEhcache("cache1");
		cache.put(new Element(1, "test"));
		System.out.println(cache.get(1));
	}
}