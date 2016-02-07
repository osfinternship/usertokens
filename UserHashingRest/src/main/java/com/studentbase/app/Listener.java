package com.studentbase.app;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

public class Listener implements javax.servlet.ServletContextListener {

	//logger
	final static Logger LOG = Logger.getLogger(Listener.class);

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub	
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		LOG.info("Listener init1" + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
}