package com.studentbase.app.temp.dao;

import java.util.List;

import com.studentbase.app.temp.UserStrategy;
import com.studentbase.app.temp.entity.UserCassandra;

public interface UserCassandraStrategy extends UserStrategy<UserCassandra>{

	@Override
	UserCassandra findById(String id);

	@Override
	UserCassandra findByLogin(String login);

	@Override
	boolean authentificate(String username, String password);

	@Override
	void saveUser(UserCassandra user);

	@Override
	void updateUser(UserCassandra user);

	@Override
	void deleteUserById(String id);

	@Override
	List<UserCassandra> findAllUsers();

}
