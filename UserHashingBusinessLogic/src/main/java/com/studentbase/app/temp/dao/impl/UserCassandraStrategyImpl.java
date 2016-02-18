package com.studentbase.app.temp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.studentbase.app.CassandraClient;
import com.studentbase.app.temp.dao.UserCassandraStrategy;
import com.studentbase.app.temp.entity.UserCassandra;

public class UserCassandraStrategyImpl implements UserCassandraStrategy {

	//logger
    final static Logger LOG = Logger.getLogger(UserCassandraStrategyImpl.class);

	private static Session session = CassandraClient.getCluster().connect();

	private static MappingManager mappingManager;
	
	@Override
	public List<UserCassandra> findAllUsers() {
		mappingManager = new MappingManager(session);
		Mapper<UserCassandra> mapper = mappingManager.mapper(UserCassandra.class);
		List<UserCassandra> lists = new ArrayList<>();
		ResultSet resultSet =  session.execute("SELECT * FROM mykeyspace.users;");
		
		Result<UserCassandra> result = mapper.map(resultSet);
		lists = result.all();
		return lists;
	}

	@Override
	public UserCassandra findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserCassandra findByLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean authentificate(String username, String password) {
		
        if(session.execute("SELECT * FROM users user where user.login = '" + username + "' and user.password = '" + password + "'")
        		.one() != null) {
        	LOG.info("Credentials is true");
        	return true;
        }

    	LOG.info("Credentials is false");
    	return false;
	}

	@Override
	public void saveUser(UserCassandra user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(UserCassandra user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUserById(int id) {
		// TODO Auto-generated method stub
		
	}


}
