package com.example.musicserver.controller;

import com.example.musicserver.pojo.Fans;
import com.example.musicserver.service.FansService;
import com.example.musicserver.service.impl.FansServiceImpl;
import com.example.musicserver.utils.Result;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fans")
public class FansController {

    @Autowired
    FansServiceImpl fansService;

//    获取我的关注
    @GetMapping("/myFocus")
    public Result getMyFocus(@RequestParam("userId") int userId){
        List<Fans> list = fansService.getMyFocus(userId);
        return Result.success(list);
    }
//    获取我的粉丝
    @GetMapping("/myFans")
    public Result getMyFans(@RequestParam("userId") int userId){
        List<Fans> list = fansService.getMyFans(userId);
        return Result.success(list);
    }
//    我的关注数量
    @GetMapping("/focusCount")
    public Result getFocusCount(@RequestParam("userId") int userId){
        int count = fansService.getFocusCount(userId);
        return Result.success(count);
    }
//    我的粉丝数量
    @GetMapping("/fansCount")
    public Result getFansCount(@RequestParam("userId") int userId){
        int count = fansService.getFansCount(userId);
        return Result.success(count);
    }
//    判断歌手是否为我的关注
    @GetMapping("/singerIsFocus")
    public Result singerIsFocus(@RequestParam("userId") int userId,@RequestParam("singerId") int singerId){
        int count = fansService.singerIsFocus(userId, singerId);
        return Result.success(count);
    }
    //    判断用户是否为我的关注
    @GetMapping("/userIsFocus")
    public Result userIsFocus(@RequestParam("userId") int userId,@RequestParam("myId")int myId){
        int count = fansService.userIsFocus(userId, myId);
        return Result.success(count);
    }
//    判断用户是否为我的粉丝
    @GetMapping("/userIsFans")
    public Result userIsFans(@RequestParam("userId") int userId,@RequestParam("myId")int myId){
        int count = fansService.userIsFans(userId, myId);
        return Result.success(count);
    }
//    添加关注
    @PostMapping("/addFocus")
    public Result addFocus(@RequestParam("userId")int userId,@RequestParam("myId") int myId,@RequestParam("type") int type){
        fansService.addFocus(userId, myId, type);
        return Result.success();
    }
//    取消关注
    @DeleteMapping("delFocus")
    public Result delFocus(@RequestParam("userId")int userId,@RequestParam("myId") int myId,@RequestParam("type") int type){
        fansService.delFocus(userId, myId, type);
        return Result.success();
    }
}
