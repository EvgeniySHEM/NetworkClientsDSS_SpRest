package ru.sanctio.dao;

public interface DBManagerUser {
    boolean checkUser(String login, String password);
}
