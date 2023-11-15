package com.example.musicserver.service.impl;

import com.example.musicserver.mapper.RootMapper;
import com.example.musicserver.mapper.UserMapper;
import com.example.musicserver.pojo.Root;
import com.example.musicserver.service.RootService;
import com.example.musicserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RootServiceImpl implements RootService {
    @Autowired
    RootMapper rootMapper;
    @Override
    public Root login(String username, String password) {
        return rootMapper.login(username,password);
    }
}
