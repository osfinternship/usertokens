package com.studentbase.app.temp.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.studentbase.app.HibernateUtil;
import com.studentbase.app.dao.Impl.UserDAOImpl;
import com.studentbase.app.temp.dao.UserMySQLStrategy;
import com.studentbase.app.temp.entity.UserMySQL;

public class UserMySQLStrategyImpl implements UserMySQLStrategy {

	//logger
    final static Logger LOG = Logger.getLogger(UserMySQLStrategyImpl.class);

	private static Session session = HibernateUtil.getSessionFactory().openSession();

	@Override
	public UserMySQL findById(int id) {
        LOG.info("Get by id: " + id);
        return (UserMySQL) session.get(UserMySQL.class, id);
	}

	@Override
	public UserMySQL findByLogin(String login) {
        LOG.info("Get by login: " + login);
        return (UserMySQL) session.createQuery("from User user where user.login = ?")
                .setParameter(0, login)
                .uniqueResult();
	}

	@Override
	public boolean authentificate(String username, String password) {
        if(session.createQuery("from UserMySQL user where user.login = ? and user.password = ?")
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
	public void saveUser(UserMySQL user) {
        try {
            session.save(user);

            session.beginTransaction().commit();
            LOG.info("User saved into db");

        } catch (HibernateException e) {
            LOG.error("Hibernate error: " + e);
        }
	}

	@Override
	public void updateUser(UserMySQL user) {
        try {
            session.createQuery("update UserMySQL user set user.login = ?, user.password = ?, user.updatedAt = ?, user.enabled = ?"
            		+ " where user.id = ?")
            .setParameter(0, user.getLogin())
            .setParameter(1, user.getPassword())
            .setParameter(2, user.getUpdatedAt())
            .setParameter(3, user.isEnabled())
            .setParameter(4, user.getId())
            .executeUpdate();

            session.beginTransaction().commit();
            LOG.info("User updated in db");

        } catch (HibernateException e) {
            LOG.error("Hibernate error: " + e);
        }
	}

	@Override
	public void deleteUserById(int id) {
        try {
            session.createQuery("delete from UserMySQL user where user.id = ?")
                    .setParameter(0, id)
                    .executeUpdate();

            session.beginTransaction().commit();
            LOG.info("User deleted from db");

        } catch (HibernateException e) {
            LOG.error("Hibernate error: " + e);
        }
	}

	@Override
	public List<UserMySQL> findAllUsers() {
        LOG.info("Get list of users");
        return (List<UserMySQL>) session.createCriteria(UserMySQL.class).list();
	}

}
