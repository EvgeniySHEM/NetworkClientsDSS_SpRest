package ru.sanctio.service;

import java.io.Serializable;

public interface UserService extends Serializable {
    boolean checkUser(String login, String password);

}
