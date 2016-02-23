/*package com.studentbase.app;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.studentbase.app.temp.UserStrategy;
import com.studentbase.app.temp.dao.impl.UserCassandraStrategyImpl;
import com.studentbase.app.temp.entity.AbstractUser;
import com.studentbase.app.temp.entity.UserCassandra;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PerformanceCassandraTest {
	//logger
	final static Logger LOG = Logger.getLogger(PerformanceCassandraTest.class);

	private static UserStrategy userStrategy;
	
	public static void setUserStrategy(UserStrategy userStrategy1) {
		userStrategy = userStrategy1;
	}

	static List<UserCassandra> users = null;
	static UUID lastId = null;
	static long start = 0;
	static long end = 0;
	

	@BeforeClass
	public static void beforeTest() {
		LOG.info("Start all tests");
		start = System.currentTimeMillis();
		setUserStrategy(new UserCassandraStrategyImpl());
	}
	
	@AfterClass
	public static void afterTest() {
		end = System.currentTimeMillis(); // 15971 ms
		LOG.info("End all tests, time between running all tests: " + (end - start));
	}

	private static UserCassandra newUser() {
		AbstractUser newUser = new UserCassandra();
		newUser.setLogin("new login");
		newUser.setPassword(md5Apache("new password"));
		newUser.setRole("user");
		newUser.setCreatedAt(new Date());
		newUser.setUpdatedAt(new Date());
		newUser.setEnabled(true);
		return (UserCassandra) newUser;
	}

	@Test // 4688 ms
	public void test01Insert() {
		LOG.info("Inserting...");
		long start = System.currentTimeMillis();
		
		for(int i = 0; i < 1_000; i++) {
			userStrategy.saveUser(newUser());
		}
		long end = System.currentTimeMillis();
		
		LOG.info("End inserting, difference= " + (end - start));
	}
	
	@Test // 931 ms
	public void test02GetAll() {
		LOG.info("Get all operation...");
		long start = System.currentTimeMillis();

		users = userStrategy.findAllUsers();
		LOG.info(users);
		
		long end = System.currentTimeMillis();

		lastId = users.get(0).getIid();
		
		LOG.info("End get all operation, difference= " + (end - start));
	}

	@Test // 28 ms
	public void test03GetById() {
		LOG.info("Get by id operation...");
		long start = System.currentTimeMillis();

		LOG.info(userStrategy.findById(lastId.toString()));
		
		long end = System.currentTimeMillis();

		LOG.info("End by id operation, difference= " + (end - start));	
	}

	@Test // 3055 ms
	public void test04Update() {
		LOG.info("Update operation...");
		
		long start = System.currentTimeMillis();

		for(int i = 1; i < users.size(); i++) {
			users.get(i).setLogin("new login");
			userStrategy.updateUser(users.get(i));
		}
		
		long end = System.currentTimeMillis();

		LOG.info("End update operation, difference= " + (end - start));	

	}

	@Test // 3045 ms
	public void test05Delete() {
		LOG.info("Delete operation...");
		
		long start = System.currentTimeMillis();

		for(int i = 1; i < users.size(); i++) {
			userStrategy.deleteUserById(users.get(i).getIid().toString());
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