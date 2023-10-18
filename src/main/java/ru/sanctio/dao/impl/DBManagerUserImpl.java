package ru.sanctio.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sanctio.dao.DBManagerUser;

import java.util.List;

@Repository
public class DBManagerUserImpl implements DBManagerUser {

    private final SessionFactory sessionFactory;

    @Autowired
    public DBManagerUserImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean checkUser(String login, String password) {
        String SQL_QUERY = "SELECT * FROM users WHERE login = :login and password = :password";
        Session session = sessionFactory.getCurrentSession();

        List resultList = session.createNativeQuery(SQL_QUERY)
                .setParameter("login", login)
                .setParameter("password", password).getResultList();

        return !resultList.isEmpty();
    }
}
