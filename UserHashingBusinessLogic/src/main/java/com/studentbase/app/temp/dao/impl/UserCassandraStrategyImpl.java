package com.studentbase.app.temp.dao.impl;

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
	public UserCassandra findById(String id) {
		mappingManager = new MappingManager(session);
		Mapper<UserCassandra> mapper = mappingManager.mapper(UserCassandra.class);
		ResultSet resultSet = session.execute("SELECT * FROM mykeyspace.users WHERE id = " + id + ";");
		
		Result<UserCassandra> result = mapper.map(resultSet);
		return result.one();
	}

	@Override
	public UserCassandra findByLogin(String login) {
		mappingManager = new MappingManager(session);
		Mapper<UserCassandra> mapper = mappingManager.mapper(UserCassandra.class);
		ResultSet resultSet = session.execute("SELECT * FROM mykeyspace.users WHERE login = '" + login + "';");
		
		Result<UserCassandra> result = mapper.map(resultSet);
		return result.one();
	}

	@Override
	public boolean authentificate(String username, String password) {
		
        if(session.execute("SELECT * FROM mykeyspace.users WHERE login = '" + username + "' AND password = '" + password + "' ALLOW FILTERING;")
        		.one() != null) {
        	LOG.info("Credentials is true");
        	return true;
        }

    	LOG.info("Credentials is false");
    	return false;
	}

	@Override
	public void saveUser(UserCassandra user) {
		
		try {
			session.execute("INSERT INTO mykeyspace.users(id, login, password, role, enabled) " +
					"VALUES(now(), '" + user.getLogin() + "', '" + user.getPassword() +"', '" + user.getRole() + "', " + user.isEnabled() + ");");
			LOG.info("User saved");
		} catch (Exception e) {
            LOG.error("Error: " + e.getMessage());
		}
	}

	@Override
	public void updateUser(UserCassandra user) {
		try {
			session.execute("UPDATE mykeyspace.users SET password = '" + user.getPassword() + "', role = '" + user.getRole() + "', enabled = ", user.isEnabled() + ";");
			LOG.info("User updated");
		} catch (Exception e) {
            LOG.error("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteUserById(String id) {
		try {
			session.execute("DELETE FROM mykeyspace.users WHERE id = " + id + ";");
			LOG.info("User deleted");
		} catch (Exception e) {
            LOG.error("Error: " + e.getMessage());
		}
	}

	@Override
	public List<UserCassandra> findAllUsers() {
		mappingManager = new MappingManager(session);
		Mapper<UserCassandra> mapper = mappingManager.mapper(UserCassandra.class);
		ResultSet resultSet =  session.execute("SELECT * FROM mykeyspace.users;");
		
		Result<UserCassandra> result = mapper.map(resultSet);
		return result.all();
	}
}
