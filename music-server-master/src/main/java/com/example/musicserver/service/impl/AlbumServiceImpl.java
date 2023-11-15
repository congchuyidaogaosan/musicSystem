package com.example.musicserver.service.impl;

import com.example.musicserver.mapper.AlbumMapper;
import com.example.musicserver.pojo.Album;
import com.example.musicserver.pojo.Page;
import com.example.musicserver.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumMapper albumMapper;

    @Override
    public List<Album> queryAlbum(int startIndex,int pageSize) {
        return albumMapper.queryAlbum(startIndex,pageSize);
    }

    @Override
    public List<Album> queryAlbumByName(String albumName) {
        return albumMapper.queryAlbumByName(albumName);
    }

    @Override
    public List<Album> albumOfSinger(int singerId) {
        return albumMapper.albumOfSinger(singerId);
    }

    @Override
    public Album queryAlbumById(int albumId) {
        return albumMapper.queryAlbumById(albumId);
    }

    @Override
    public int countOfAlbum() {
        return albumMapper.countOfAlbum();
    }

    @Override
    public void addAlbum(String albumName, int albumSinger, Date albumTime) {
        albumMapper.addAlbum(albumName,albumSinger,albumTime);
    }


    @Override
    public void putAlbum(Album album) {
        albumMapper.putAlbum(album);
    }

    @Override
    public void delAlbum(int albumId) {
        albumMapper.delAlbum(albumId);
    }

    @Override
    public int albumOfName(String albumName) {
       return albumMapper.albumOfName(albumName);
    }

    @Override
    public Album IdAlbumOfName(String albumName) {
        return  albumMapper.IdAlbumOfName(albumName);
    }
}
