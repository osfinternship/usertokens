package com.studentbase.app;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

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
		cm = CacheManager.newInstance();
		LOG.info("Cache created");
		
	}
}