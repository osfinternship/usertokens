package com.studentbase.app;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import cache.CacheAPI;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

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
	
	public static String md5Apache(String st) {
	    String md5Hex = DigestUtils.md5Hex(st);
	 
	    return md5Hex;
	}

	CacheAPI<Integer, String> cache1 = new CacheAPI<>("cache1");		
	CacheAPI<Integer, String> cache2 = new CacheAPI<>("cache1");
	
	@Test
	public void test11() {
		
		cache1.put(1, "oleg");
		
		System.out.println(cache1.get(1));
		System.out.println(cache1.expired(1));
		System.out.println(cache1.getCache().getName());
		
		cache2.put(1, "oleg");
		
		System.out.println(cache2.get(1));
		System.out.println(cache2.expired(1));
		System.out.println(cache2.getCache().getName());	
	}
	
	@Test
	public void test12() {
		cache1.remove(1);
	}
	
    private int amountCacheElements = 0;
    
	public void addElementToCache(Element element) {

		System.out.println(amountCacheElements);
		
		//1. Create a cache manager
		CacheManager cm = CacheManager.newInstance();

		//2. Get a cache called "cache1", declared in ehcache.xml
		Cache cache = cm.getCache("cache1");

		//3. Put few elements in cache
		cache.put(element);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//4. Get element from cache
		Element ele = cache.get(0);

		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//5. Print out the element
		String output = (ele == null ? null : ele.getObjectValue().toString());
		LOG.info("<><><> " + output);
		
		//6. Is key in cache?
		LOG.info("<><><> " + cache.isKeyInCache(0));
		LOG.info("<><><> " + cache.isKeyInCache(2));
		
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//7. shut down the cache manager
		cm.shutdown();
	}

}