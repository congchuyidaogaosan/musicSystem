package com.example.musicserver.service;

import com.example.musicserver.pojo.Album;
import com.example.musicserver.pojo.Page;

import java.util.Date;
import java.util.List;

public interface AlbumService {

    List<Album> queryAlbum(int startIndex,int pageSize);

    List<Album> queryAlbumByName(String albumName);

    Album queryAlbumById(int albumId);

    List<Album> albumOfSinger(int singerId);

    int countOfAlbum();

    void addAlbum(String albumName, int albumSinger, Date albumTime);

    void putAlbum(Album album);

    void delAlbum(int albumId);

    int albumOfName(String albumName);

    Album IdAlbumOfName(String albumName);
}
