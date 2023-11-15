package com.example.musicserver.service.impl;

import com.example.musicserver.mapper.UserMapper;
import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.User;
import com.example.musicserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public int querydelUser(int id) {
        return userMapper.querydelUser(id);
    }
    @Override
    public List<User> queryAllUser() {
        return userMapper.queryAllUser();
    }

    @Override
    public List<User> queryUserByName(String userName) {
        return userMapper.queryUserByName(userName);
    }

    @Override
    public User loginPhone(String userPhone) {
        return userMapper.loginPhone(userPhone);
    }

    @Override
    public int userByPhone(String userPhone) {
        return userMapper.userByPhone(userPhone);
    }

    @Override
    public int userByAccount(String account) {
        return userMapper.userByAccount(account);
    }

    @Override
    public int countOfUser() {
        return userMapper.countOfUser();
    }

    @Override
    public User userById(int userId) {
        return userMapper.userById(userId);
    }

    @Override
    public int register(User user) {
        return userMapper.register(user);
    }

    @Override
    public User login(String account, String password) {
        return userMapper.login(account,password);
    }

    @Override
    public void putUser(User user) {
        userMapper.putUser(user);
    }
}
