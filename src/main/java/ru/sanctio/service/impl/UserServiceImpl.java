package ru.sanctio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sanctio.dao.DBManagerUser;
import ru.sanctio.service.UserService;

import java.io.Serializable;

@Service
public class UserServiceImpl implements UserService, Serializable {

    private final DBManagerUser dbManagerUser;

    @Autowired
    public UserServiceImpl(DBManagerUser dbManagerUser) {
        this.dbManagerUser = dbManagerUser;
    }

    @Transactional
    public boolean checkUser(String login, String password) {
        return dbManagerUser.checkUser(login, password);
    }
}
