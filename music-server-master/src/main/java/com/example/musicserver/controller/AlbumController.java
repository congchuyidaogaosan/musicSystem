package com.example.musicserver.controller;

import com.example.musicserver.pojo.Album;
import com.example.musicserver.pojo.Page;
import com.example.musicserver.service.impl.AlbumServiceImpl;
import com.example.musicserver.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    AlbumServiceImpl albumService;

    @GetMapping("/queryAlbums")
    public Result queryAllAlbum(@RequestParam("pageNum")int pageNum,@RequestParam("pageSize") int pageSize){
        List<Album> albumList=albumService.queryAlbum((pageNum-1)*pageSize,pageSize);
        int count=albumService.countOfAlbum();
        Map<String,Object> map=new HashMap<>();
        map.put("list",albumList);
        map.put("totalSize",count);
        return Result.success(map);

    }
//    查询专辑信息
    @GetMapping("/albumById")
    public Result albumById(@RequestParam("albumId") int albumId){
        Album album = albumService.queryAlbumById(albumId);
        return Result.success(album);

    }
//    通过名字查询专辑
    @GetMapping("/albumByName")
    public Result albumByName(@RequestParam("albumName") String albumName){
        List<Album> list= albumService.queryAlbumByName(albumName);
        return Result.success(list);
    }

//    根据歌手获取专辑
    @GetMapping("/albumOfSinger")
    public Result albumOfSinger(@RequestParam("singerId") int singerId){
        List<Album> albumList = albumService.albumOfSinger(singerId);
        return Result.success(albumList);
    }



    @PutMapping("/putAlbum")
    public Result putAlbum(@RequestBody Album album){
        albumService.putAlbum(album);
        return Result.success();
    }

    @DeleteMapping("/delAlbum/{id}")
    public Result delAlbum(@PathVariable int id){
        albumService.delAlbum(id);
        return Result.success();
    }
}
