package com.studentbase.app;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.studentbase.app.entity.User;
import com.studentbase.app.service.UserService;
import com.studentbase.app.service.Impl.UserServiceImpl;

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
	public void test1() {
/*		UserService userService = new UserServiceImpl();
		User user = userService.findById(1);

		User test = new User();
		test.setPassword("test+++");
		System.out.println(user);

		user.setPassword(test.getPassword());
		
		userService.updateUser(user);
		System.out.println(user);
		System.out.println(test);
*/	}
}