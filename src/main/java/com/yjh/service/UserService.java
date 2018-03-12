package com.yjh.service;

import com.yjh.pojo.User;

import java.util.List;

public interface UserService {
    int add(User user);
    int delete(int id);
    int update(User user);
    User get(int id);
    List<User> list();
    boolean isExist(String name);
    User get(String name, String password);
}
