package com.example.musicserver.service;

import com.example.musicserver.pojo.Singer;
import java.util.List;

public interface SingerService {

    List<Singer> queryAllSinger(int startIndex,int pageSize);

    List<Singer> topSinger();

    List<Singer> querySingerByType(int startIndex,int pageSize,int singerType);

    List<Singer> querySingerByName(String singerName);

    int countOfSinger();

    int countOfSingerByType(int singerType);

    Singer querySingerById(int singerId);

    void addSinger(String singerName);

    void putSinger(Singer singer);

    void delSinger(int singerId);

    int SingerByName(String singName);

    Singer IdSingerByName (String singerName);
}
