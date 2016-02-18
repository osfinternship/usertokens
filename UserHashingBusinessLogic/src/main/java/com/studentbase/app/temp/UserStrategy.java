package com.studentbase.app.temp;

import java.util.List;

public interface UserStrategy<T> {
    
	T findById(int id);

    T findByLogin(String login);
    
    boolean authentificate(String username, String password);

    void saveUser(T user);

    void updateUser(T user);

    void deleteUserById(int id);

    List<T> findAllUsers();

}
