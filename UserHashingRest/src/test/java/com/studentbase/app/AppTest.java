package com.studentbase.app;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

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
	 
		// cache manager
		static CacheManager cacheManager;
		
		// cache instance
		static Ehcache cache;
		
		// getter and setter of cache manager
		public static CacheManager getCacheManager() {
			return cacheManager;
		}

		public static void setCacheManager(CacheManager cacheManager) {
			cache = cacheManager.getEhcache("weather_cache");
		}

	 @Test
	 public void test1() {	 
//		 System.out.println(cache.get("Ivano-Frankivsk"));
//		 System.out.println(cache.get("Lviv"));
	 }
	 
	@Test
	public void test() throws ParseException {
		SimpleDateFormat dateF = new SimpleDateFormat("dd-M-yyyy");
		System.out.println();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = dateF.format(new Date()) + " 23:59:59";
		Date date = sdf.parse(dateInString);
		System.out.println(date.getTime()); //Tue Aug 31 10:20:56 SGT 1982
        
		System.out.println(System.currentTimeMillis());
		
		System.out.println((date.getTime() - System.currentTimeMillis()) / 1000);


	}
}