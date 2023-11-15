package com.example.musicserver.mapper;

import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.Song;
import com.example.musicserver.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    //查询所有用户，用于管理端
    int querydelUser(int id);
    List<User> queryAllUser();
    //查询所有用户数量，用于分页计算总页数
    int countOfUser();
    //通过Id查询用户信息
    User userById(int userId);
    //注册
    int register(User user);
    //登录
    User login(@Param("account") String account,@Param("password") String password);
    //手机号登录
    User loginPhone(String userPhone);
    //注册时查询手机号是否已经被使用
    int userByPhone(String userPhone);
    //注册时查询账号是否已经被使用
    int userByAccount(String account);
    //修改用户信息
    void putUser(User user);

    List<User> queryUserByName(String userName);
}
