package com.studentbase.app.service.Impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;

import com.studentbase.app.dao.UserDAO;
import com.studentbase.app.dao.Impl.UserDAOImpl;
import com.studentbase.app.entity.User;
import com.studentbase.app.service.UserService;

public class UserServiceImpl implements UserService {

	UserDAO userDAO = new UserDAOImpl();
	
    @Override
    public User findById(int id) {
        return userDAO.findById(id);
    }

    @Override
    public User findByLogin(String login) {
        return userDAO.findByLogin(login);
    }

    @Override
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUserById(int id) {
        userDAO.deleteUserById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }
}
