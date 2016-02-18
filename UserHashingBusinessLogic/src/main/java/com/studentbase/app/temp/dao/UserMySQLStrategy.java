package com.studentbase.app.temp.dao;

import java.util.List;

import com.studentbase.app.temp.UserStrategy;
import com.studentbase.app.temp.entity.UserMySQL;

public interface UserMySQLStrategy extends UserStrategy<UserMySQL> {

	@Override
	UserMySQL findById(int id);

	@Override
	UserMySQL findByLogin(String login);

	@Override
	boolean authentificate(String username, String password);

	@Override
	void saveUser(UserMySQL user);

	@Override
	void updateUser(UserMySQL user);

	@Override
	void deleteUserById(int id);

	@Override
	List<UserMySQL> findAllUsers();

}
