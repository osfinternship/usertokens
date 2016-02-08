package com.studentbase.app.service;

import java.util.List;

import com.studentbase.app.entity.User;

public interface UserService {

    User findById(int id);

    User findByLogin(String login);

    boolean authentificate(String username, String password);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(int id);

    List<User> findAllUsers();

}
