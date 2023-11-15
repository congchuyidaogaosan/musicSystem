package com.example.musicserver.service.impl;

import com.example.musicserver.mapper.SongMapper;
import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.Song;
import com.example.musicserver.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    SongMapper songMapper;

    @Override
    public Song querySongById(int listId) {
        return songMapper.querySongById(listId);
    }

    @Override
    public List<Song> queryAllSong() {
        return songMapper.queryAllSong();
    }

    @Override
    public int countOfSongs() {
        return songMapper.countOfSongs();
    }

    @Override
    public List<Song> querySongByName(String songName) {
        return songMapper.querySongByName(songName);
    }

    @Override
    public List<Song> querySongOfList(int listId) {
        return songMapper.querySongOfList(listId);
    }

    @Override
    public List<Song> querySongOfSinger(int singerId) {
        return songMapper.querySongOfSinger(singerId);
    }

    @Override
    public void addSongOfUser(int userId, int songId) {
        songMapper.addSongOfUser(userId, songId);
    }

    @Override
    public void delSongOfUser(int userId, int songId) {
        songMapper.delSongOfUser(userId, songId);
    }

    @Override
    public List<Song> querySongOfUser(int userId) {
        return songMapper.querySongOfUser(userId);
    }

    @Override
    public List<Song> querySongOfAlbum(int albumId) {
        return songMapper.querySongOfAlbum(albumId);
    }

    @Override
    public void addSong(Song song) {
        songMapper.addSong(song);
    }

    @Override
    public void putSong(Song song) {
        songMapper.putSong(song);
    }

    @Override
    public void delSong(int songId) {
        songMapper.delSong(songId);
    }

    @Override
    public int songByName(String songName) {
        return songMapper.songByName(songName);
    }
}
