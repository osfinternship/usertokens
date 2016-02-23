/*package com.studentbase.app;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.studentbase.app.temp.UserStrategy;
import com.studentbase.app.temp.dao.impl.UserMySQLStrategyImpl;
import com.studentbase.app.temp.entity.AbstractUser;
import com.studentbase.app.temp.entity.UserMySQL;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PerformanceMySQLTest {
	//logger
	final static Logger LOG = Logger.getLogger(PerformanceMySQLTest.class);

	private static UserStrategy userStrategy;
	
	public static void setUserStrategy(UserStrategy userStrategy1) {
		userStrategy = userStrategy1;
	}

	static List<UserMySQL> users = null;
	static int lastId = 0;
	static long start = 0;
	static long end = 0;
	
	@BeforeClass
	public static void beforeTest() {
		LOG.info("Start all tests");
		start = System.currentTimeMillis();
		setUserStrategy(new UserMySQLStrategyImpl());
	}
	
	@AfterClass
	public static void afterTest() {
		end = System.currentTimeMillis(); // 273251 ms
		LOG.info("End all tests, time between running all tests: " + (end - start));
	}

	private static UserMySQL newUser() {
		AbstractUser newUser = new UserMySQL();
		newUser.setLogin("new login");
		newUser.setPassword(md5Apache("new password"));
		newUser.setRole("user");
		newUser.setCreatedAt(new Date());
		newUser.setUpdatedAt(new Date());
		newUser.setEnabled(true);
		return (UserMySQL) newUser;
	}
	
	@Test // 80978 ms
	public void test01Insert() {
		LOG.info("Inserting...");
		long start = System.currentTimeMillis();
		
		for(int i = 0; i < 1_000; i++) {
			userStrategy.saveUser(newUser());
			LOG.info("User[" + i + "]=" + newUser());
		}
		long end = System.currentTimeMillis();
		
		LOG.info("End inserting, difference= " + (end - start));
	}

	@Test // 996 ms
	public void test02GetAll() {
		LOG.info("Get all operation...");
		long start = System.currentTimeMillis();

		users = userStrategy.findAllUsers();
		LOG.info(users);
		
		long end = System.currentTimeMillis();

		lastId = users.get(0).getId();
		
		LOG.info("End get all operation, difference= " + (end - start));
	}

	@Test // 8 ms
	public void test03GetById() {
		LOG.info("Get by id operation...");
		long start = System.currentTimeMillis();

		LOG.info(userStrategy.findById(lastId+""));
		
		long end = System.currentTimeMillis();

		LOG.info("End by id operation, difference= " + (end - start));	
	}

	@Test // 85102 ms
	public void test04Update() {
		LOG.info("Update operation...");
		
		long start = System.currentTimeMillis();

		for(int i = users.get(0).getId(); i < users.size(); i++) {
			users.get(i).setLogin("new login");
			userStrategy.updateUser(users.get(i));
		}
		
		long end = System.currentTimeMillis();

		LOG.info("End update operation, difference= " + (end - start));	

	}

	@Test // 98473 ms
	public void test05Delete() {
		LOG.info("Delete operation...");
		
		long start = System.currentTimeMillis();

		for(int i = users.get(0).getId(); i < users.size(); i++) {
			userStrategy.deleteUserById(users.get(i).getId()+"");
		}
		
		long end = System.currentTimeMillis();

		LOG.info("End delete operation, difference= " + (end - start));	

	}

    *//**
     * MD5 hashing
     * @param st String to hashing
     * @return Md5 hashed string
     *//*
	public static String md5Apache(String st) {
	    String md5Hex = DigestUtils.md5Hex(st);
	 
	    return md5Hex;
	}
}
*/