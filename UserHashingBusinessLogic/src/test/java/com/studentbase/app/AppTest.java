package com.studentbase.app;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.studentbase.app.service.UserService;
import com.studentbase.app.service.Impl.UserServiceImpl;
import com.studentbase.app.temp.UserStrategy;
import com.studentbase.app.temp.dao.impl.UserCassandraStrategyImpl;
import com.studentbase.app.temp.dao.impl.UserMySQLStrategyImpl;
import com.studentbase.app.temp.entity.AbstractUser;
import com.studentbase.app.temp.entity.UserMySQL;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {

	//logger
	final static Logger LOG = Logger.getLogger(AppTest.class);

//	static Session session;
	
	private UserStrategy userStrategy;
	//private Session session;
	
	public void setUserStrategy(UserStrategy userStrategy) {
		this.userStrategy = userStrategy;
	}

	@Before
	public void beforeTest() {
		//session = CassandraClient.getCluster().connect();
		setUserStrategy(new UserMySQLStrategyImpl());
		
//		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	@After
	public void afterTest() {
		//session.close();
//		session.close();
	}
	
	//private Cluster cluster;

/*	public void connect(String node) {
		cluster = Cluster.builder()
		         .addContactPoint(node)
		         .build();
		Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n", 
		         metadata.getClusterName());
		for ( Host host : metadata.getAllHosts() ) {
			System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
		    host.getDatacenter(), host.getAddress(), host.getRack());
		}
	}

	public void close() {
		cluster.close();
	}*/
	
/*	@Test
	public void test1() {
		AbstractUser user = new AbstractUser();
		user.setId(10);
		user.setLogin("test1");
		user.setPassword("pass");
		user.setRole("user");
		user.setEnabled(true);
		userStrategy.saveUser(user);
	}
	
	@Test
	public void test2() {
		System.out.println(userStrategy.findAllUsers());
	}
*/}