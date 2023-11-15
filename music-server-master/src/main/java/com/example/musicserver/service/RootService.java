package com.example.musicserver.service;

import com.example.musicserver.pojo.Root;
import com.example.musicserver.pojo.User;

public interface RootService {
    Root login(String username, String password);
}
