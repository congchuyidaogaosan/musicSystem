package com.example.musicserver.service.impl;

import com.example.musicserver.mapper.SingerMapper;
import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.Singer;
import com.example.musicserver.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    SingerMapper singerMapper;
    @Override
    public List<Singer> queryAllSinger(int startIndex,int pageSize) {
        return singerMapper.queryAllSinger(startIndex, pageSize);
    }

    @Override
    public List<Singer> topSinger() {
        return singerMapper.topSinger();
    }

    @Override
    public List<Singer> querySingerByType(int startIndex, int pageSize, int singerType) {
        return singerMapper.querySingerByType(startIndex, pageSize, singerType);
    }

    @Override
    public List<Singer> querySingerByName(String singerName) {
        return singerMapper.querySingerByName(singerName);
    }

    @Override
    public int countOfSinger() {
        return singerMapper.countOfSinger();
    }

    @Override
    public int countOfSingerByType(int singerType) {
        return singerMapper.countOfSingerByType(singerType);
    }

    @Override
    public Singer querySingerById(int singerId) {
        return singerMapper.querySingerById(singerId);
    }

    @Override
    public void addSinger(String singerName) {
        singerMapper.addSinger(singerName);
    }

    @Override
    public void putSinger(Singer singer) {
        singerMapper.putSinger(singer);
    }

    @Override
    public void delSinger(int singerId) {
        singerMapper.delSinger(singerId);
    }

    @Override
    public int SingerByName(String singName) {
       return singerMapper.SingerByName(singName);
    }

    @Override
    public Singer IdSingerByName(String singerName) {
        return singerMapper.IdSingerByName(singerName);
    }
}
