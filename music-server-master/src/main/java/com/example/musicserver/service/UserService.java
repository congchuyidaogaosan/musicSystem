package com.example.musicserver.service;

import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.User;

import java.util.List;
//方法注释在Mapper类
public interface UserService {
    int querydelUser(int id);
    List<User> queryAllUser();

    List<User> queryUserByName(String userName);

    int countOfUser();

    User userById(int userId);

    int register(User user);

    User login(String account,String password);

    User loginPhone(String userPhone);

    int userByPhone(String userPhone);

    int userByAccount(String account);

    void putUser(User user);
}
