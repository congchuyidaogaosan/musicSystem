package com.example.musicserver.controller;

import com.example.musicserver.pojo.Lists;
import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.Song;
import com.example.musicserver.service.impl.ListsServiceImpl;
import com.example.musicserver.utils.Result;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lists")
public class ListsController {

    @Autowired
    ListsServiceImpl listsService;
    @Autowired
    RedisTemplate<String,String> redisTemplate;

//    获取全部歌单
    @GetMapping("/allLists")
    public Result allLists(@RequestParam("pageNum")int pageNum, @RequestParam("pageSize") int pageSize){
        List<Lists> list=listsService.queryLists((pageNum-1)*pageSize,pageSize);
        int count=listsService.countOfList();
        Map<String,Object> map =new HashMap<>();
        map.put("list",list);
        map.put("totalSize",count);
        return Result.success(map);
    }

//    通过类型查询歌单
    @GetMapping("/listByType")
    public Result listByType(@RequestParam("pageNum")int pageNum,
                             @RequestParam("pageSize") int pageSize,
                             @RequestParam("listType")int listType){
        List<Lists> list=listsService.queryListsByType((pageNum-1)*pageSize,pageSize,listType);
        int count=listsService.countOfListByType(listType);
        Map<String,Object> map =new HashMap<>();
        map.put("list",list);
        map.put("totalSize",count);
        return Result.success(map);
    }

//    通过名字查询歌单
    @GetMapping("/listByName")
    public Result listByName(@RequestParam("listName") String listName){
        List<Lists> list= listsService.queryListByName(listName);
        return Result.success(list);
    }

    //推荐歌单
    @GetMapping("/topLists")
    public Result topLists(HttpServletResponse response){
        List<Lists> list=listsService.topLists();
        return Result.success(list);

    }

    //我的歌单
    @GetMapping("/listByUserId")
    public Result listByUserId(int userId){
        List<Lists> list=listsService.queryListsByUserId(userId);
        return Result.success(list);
    }
//  我的收藏歌单
    @GetMapping("/colListByUserId")
    public Result colListByUserId(int userId){
        List<Lists> list=listsService.colListsByUserId(userId);
        return Result.success(list);
    }

//    进入歌单后判断是否收藏了歌单
    @GetMapping("/isCol")
    public Result isCol(@RequestParam("listId") int listId,@RequestParam("userId") int userId){
        int isCol=listsService.isCol(listId, userId);
        return Result.success(isCol);
    }

//    收藏歌单
    @PostMapping("/addCol")
    public Result addCol(@RequestParam("listId") int listId,@RequestParam("userId") int userId){
        listsService.addCol(listId, userId);
        return Result.success();
    }

//    取消收藏歌单
    @DeleteMapping("/delCol/{listId}/{userId}")
    public Result delCol(@PathVariable("listId") int listId,@PathVariable("userId") int userId){
        listsService.delCol(listId, userId);
        return Result.success();
    }

//    歌单内容
    @GetMapping("/listById")
    public Result listById(int listId){
        Lists list=listsService.userOfList(listId);
        String key="list_"+listId;
//        以增量的方式（默认增量为1）将long值存储在变量中(value为其他类型时报错)，返回最新值
        redisTemplate.opsForValue().increment(key);
        return Result.success(list);
    }
//    歌曲添加到歌单
    @PostMapping("/addSongToList/{songId}/{listId}")
    public Result addSongToList(@PathVariable("songId") int songId,@PathVariable("listId") int listId){
        listsService.addSongToList(songId,listId);
        return Result.success();
    }

//    新增歌单
    @PostMapping("/addLists")
    public Result addList(@RequestBody Lists lists){
        int key=listsService.addLists(lists);
//        新增一个字符串的值,第一个为key 第二个为值
        redisTemplate.opsForValue().set("list_"+lists.getListId(),"0");
        return Result.success();
    }


    @PutMapping("putLists")
    public Result putLists(@RequestBody Lists lists){
        listsService.putLists(lists);
        return Result.success();
    }

    @DeleteMapping("/delLists/{id}")
    public Result delLists(@PathVariable int id){
        listsService.delLists(id);
        return Result.success();
    }
}
