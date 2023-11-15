package com.example.musicserver.service.impl;

import com.example.musicserver.mapper.FansMapper;
import com.example.musicserver.pojo.Fans;
import com.example.musicserver.service.FansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FansServiceImpl implements FansService {

    @Autowired
    FansMapper fansMapper;

    @Override
    public List<Fans> getMyFans(int userId) {
        return fansMapper.getMyFans(userId);
    }

    @Override
    public List<Fans> getMyFocus(int userId) {
        return fansMapper.getMyFocus(userId);
    }

    @Override
    public int getFocusCount(int userId) {
        return fansMapper.getFocusCount(userId);
    }

    @Override
    public int getFansCount(int userId) {
        return fansMapper.getFansCount(userId);
    }

    @Override
    public int userIsFocus(int userId, int myId) {
        return fansMapper.userIsFocus(userId, myId);
    }

    @Override
    public int userIsFans(int userId, int myId) {
        return fansMapper.userIsFans(userId, myId);
    }

    @Override
    public int singerIsFocus(int userId, int singerId) {
        return fansMapper.singerIsFocus(userId, singerId);
    }

    @Override
    public void addFocus(int userId, int myId, int type) {
        fansMapper.addFocus(userId, myId, type);
    }

    @Override
    public void delFocus(int userId, int myId, int type) {
        fansMapper.delFocus(userId, myId, type);
    }
}
