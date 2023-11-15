package com.example.musicserver.controller;

import com.example.musicserver.pojo.Album;
import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.Singer;
import com.example.musicserver.pojo.Song;
import com.example.musicserver.service.impl.AlbumServiceImpl;
import com.example.musicserver.service.impl.ListsServiceImpl;
import com.example.musicserver.service.impl.SingerServiceImpl;
import com.example.musicserver.service.impl.SongServiceImpl;
import com.example.musicserver.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    SongServiceImpl songService;

    @Autowired
    SingerServiceImpl singerService;

    @Autowired
    AlbumServiceImpl albumService;
    @Autowired
    ListsServiceImpl listsService;

    @GetMapping("/querySongs")
    public Result querySongs() {
        List<Song> list = songService.queryAllSong();
        int count = songService.countOfSongs();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("totalSize", count);
        return Result.success(map);
    }

//   获取歌单的歌曲
    @GetMapping("/songOfLists")
    public Result songByList(int listId) {
        List<Song> songList = songService.querySongOfList(listId);
        return Result.success(songList);
    }

//    通过名字查询歌曲
    @GetMapping("/songByName")
    public Result songByName(@RequestParam("songName") String songName) {
        List<Song> list = songService.querySongByName(songName);
        return Result.success(list);
    }

//    获取专辑歌曲
    @GetMapping("/songOfAlbum")
    public Result songOfAlbum(@RequestParam("albumId") int albumId) {
        List<Song> songList = songService.querySongOfAlbum(albumId);
        return Result.success(songList);
    }

//    根据歌手获取歌曲
    @GetMapping("/songOfSinger")
    public Result songOfSinger(@RequestParam("singerId") int singerId) {
        List<Song> songList = songService.querySongOfSinger(singerId);
        return Result.success(songList);
    }

    @GetMapping("/songOfUser")
    public Result songOfUser(@RequestParam("userId") int userId) {
        List<Song> song = songService.querySongOfUser(userId);
        return Result.success(song);
    }

//    收藏歌曲
    @PostMapping("/addSongOfUser")
    public Result addCol(@RequestParam("userId") int userId, @RequestParam("songId") int songId) {
        songService.addSongOfUser(userId, songId);//收藏音乐
        listsService.addSongToMyList(songId, userId);//同时放入我喜欢的音乐这个歌单里
        List<Song> song = songService.querySongOfUser(userId);
        return Result.success(song);
    }
//    取消收藏歌曲
    @DeleteMapping("/delSongOfUser/{userId}/{songId}")
    public Result delCol(@PathVariable("userId") int userId, @PathVariable("songId") int songId) {
        songService.delSongOfUser(userId, songId);//取消收藏音乐
        listsService.delSongToMyList(songId, userId);//同时删除我喜欢的音乐这个歌单里
        List<Song> song = songService.querySongOfUser(userId);
        return Result.success(song);
    }

    @PostMapping("/addSong")
    public Result addSong(@RequestBody Song song) {
        songService.addSong(song);
        return Result.success();
    }

    @PutMapping("/putSong")
    public Result putSong(@RequestBody Song song) {
        songService.putSong(song);
        return Result.success();
    }

    @DeleteMapping("/delSong/{id}")
    public Result delSong(@PathVariable int id) {
        songService.delSong(id);
        return Result.success();
    }


    @PostMapping("/upload")
    public Result fileupload(MultipartFile file, String songName, String singerName, String albumName, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int count = songService.songByName(songName);
        if (count > 0) {
            return Result.error("107", "此歌曲的名字已被绑定！");
        }

        int count2 = singerService.SingerByName(singerName);
        if (count2 == 0) {
            singerService.addSinger(singerName);
        }

        int count3 = albumService.albumOfName(albumName);
        int singerid =  singerService.IdSingerByName(singerName).getSingerId();
        if (count3 == 0) {
            Date date = new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String te = dateFormat.format(date);
            System.out.println(te);
            Date date2 = null;
            try {
                date2 = dateFormat.parse(te);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            System.out.println(date2);
            albumService.addAlbum(albumName,singerid,date2);
        }

        int albumId =  albumService.IdAlbumOfName(albumName).getAlbumId();

        String originaName = file.getOriginalFilename();
        if (!originaName.endsWith(".mp3")) {
            return Result.error("107", "文件类型不对");
        }
        String realPath = "/Users/tian/Documents/软件二班23田雷宇毕设/音乐系统/music-server-master/file/music";
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try {
            file.transferTo(new File(folder, originaName));
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/file/music/" + originaName;
            System.out.println(url);
            map.put("url", url);

            Song song= new Song();
            song.setSongFilePath(url);
            song.setSongName(songName);
            song.setSongSinger(singerid);
            song.setSongAlbum(albumId);
            songService.addSong(song);
            Result.success();
        } catch (IOException e) {
            Result.error("107", e.getMessage());
        }


    return Result.success();
    }





}
