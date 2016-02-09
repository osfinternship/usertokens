package com.studentbase.app;

import static org.apache.commons.codec.binary.Base64.decodeBase64;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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
	
	@Test
	public void test11() {
		byte[] encodedBytes = Base64.encodeBase64("139a9fie44idjeg7ec8jdi42aceg08c:b2w=".getBytes());
		System.out.println("encodedBytes " + new String(encodedBytes));
		
		byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
		System.out.println("decodedBytes " + new String(decodedBytes));
		
		String[] values = new String(Base64.decodeBase64(encodedBytes)).split(":", 2);
		for(String a : values) {
			System.out.println(a);
		}
	}
}