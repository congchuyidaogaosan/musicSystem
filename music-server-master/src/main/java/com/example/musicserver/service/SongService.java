package com.example.musicserver.service;

import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.Song;

import java.util.List;

public interface SongService {

    List<Song> queryAllSong();

    int countOfSongs();

    List<Song> querySongByName(String songName);

    List<Song> querySongOfList(int listId);

    List<Song> querySongOfAlbum(int albumId);

    List<Song> querySongOfSinger(int singerId);

    List<Song> querySongOfUser(int userId);

    void addSongOfUser(int userId,int songId);

    void delSongOfUser(int userId,int songId);

    Song querySongById(int songId);

    void addSong(Song song);

    void putSong(Song song);

    void delSong(int songId);

    int songByName(String songName);
}
