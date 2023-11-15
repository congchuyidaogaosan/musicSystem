package com.example.musicserver.mapper;

import com.example.musicserver.pojo.Album;
import com.example.musicserver.pojo.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AlbumMapper {

    List<Album> queryAlbum(@Param("startIndex")int startIndex,@Param("pageSize") int pageSize);

    Album queryAlbumById(@Param("albumId") int albumId);

    List<Album> queryAlbumByName(String albumName);

    List<Album> albumOfSinger(@Param("singerId") int singerId);

    int countOfAlbum();

    void addAlbum(String albumName, int albumSinger, Date albumTime);

    void putAlbum(Album album);

    void delAlbum(@Param("albumId") int albumId);

    int albumOfName(String albumName);

    Album IdAlbumOfName(String albumName);
}
