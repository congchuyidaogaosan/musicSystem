package com.example.musicserver.service;

import com.example.musicserver.pojo.Fans;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FansService {

    List<Fans> getMyFocus(int userId);

    List<Fans> getMyFans(int userId);

    int getFocusCount(int userId);

    int getFansCount(int userId);

    int singerIsFocus(int userId,int singerId);

    int userIsFocus(int userId,int myId);

    int userIsFans(int userId,int myId);

    void addFocus(int userId,int myId,int type);

    void delFocus(int userId,int myId,int type);
}
