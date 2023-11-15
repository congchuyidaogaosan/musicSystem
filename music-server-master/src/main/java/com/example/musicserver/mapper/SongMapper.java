package com.example.musicserver.mapper;

import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SongMapper {

     int songByName(String songName);
     List<Song> queryAllSong();

     List<Song> querySongByName(String songName);

     List<Song> querySongOfList(int listId);

     List<Song> querySongOfAlbum(int albumId);

     List<Song> querySongOfSinger(int singerId);

     List<Song> querySongOfUser(int userId);

     void addSongOfUser(@Param("userId") int userId,@Param("songId")int songId);

     void delSongOfUser(@Param("userId")int userId,@Param("songId")int songId);

     int countOfSongs();

     Song querySongById(int songId);

     void addSong(Song song);

     void putSong(Song song);

     void delSong(int songId);
}
