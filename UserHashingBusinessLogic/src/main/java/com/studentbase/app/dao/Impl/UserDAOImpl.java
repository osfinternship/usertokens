package com.studentbase.app.dao.Impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.studentbase.app.HibernateUtil;
import com.studentbase.app.dao.UserDAO;
import com.studentbase.app.entity.User;

public class UserDAOImpl implements UserDAO{

	//logger
    final static Logger LOG = Logger.getLogger(UserDAOImpl.class);

	private static Session session = HibernateUtil.getSessionFactory().openSession();

    @Override
    public User findById(int id) {
        LOG.info("Get by id: " + id);
        return (User) session.get(User.class, id);
    }

    @Override
    public User findByLogin(String login) {
        LOG.info("Get by login: " + login);
        return (User) session.createQuery("from User user where user.login = ?")
                .setParameter(0, login)
                .uniqueResult();
    }

    @Override
	public boolean authentificate(String username, String password) {
        if(session.createQuery("from User user where user.login = ? and user.password = ?")
                .setParameter(0, username)
                .setParameter(1, password)
                .uniqueResult() != null) {
        	LOG.info("Credentials is true");
        	return true;
        }

    	LOG.info("Credentials is false");
    	return false;
	}


    @Override
    public void saveUser(User user) {
        try {
            session.save(user);

            session.beginTransaction().commit();
            LOG.info("User saved into db");

        } catch (HibernateException e) {
            LOG.error("Hibernate error: " + e);
        }

    }

    @Override
    public void updateUser(User user) {
        try {
            session.update(user);

            session.beginTransaction().commit();
            LOG.info("User updated in db");

        } catch (HibernateException e) {
            LOG.error("Hibernate error: " + e);
        }

    }

    @Override
    public void deleteUserById(int id) {
        try {
            session.createQuery("delete from User user where user.id = ?")
                    .setParameter(0, id);

            session.beginTransaction().commit();
            LOG.info("User deleted from db");

        } catch (HibernateException e) {
            LOG.error("Hibernate error: " + e);
        }

    }

    @Override
    public List<User> findAllUsers() {
        LOG.info("Get list of users");
        return (List<User>) session.createCriteria(User.class).list();
    }
}
