package com.example.musicserver.controller;

import com.example.musicserver.pojo.Lists;
import com.example.musicserver.pojo.Song;
import com.example.musicserver.pojo.User;
import com.example.musicserver.service.impl.ListsServiceImpl;
import com.example.musicserver.service.impl.SongServiceImpl;
import com.example.musicserver.service.impl.UserServiceImpl;
import com.example.musicserver.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ListsServiceImpl listsService;

    @Autowired
    SongServiceImpl songService;

    @Autowired
    RedisTemplate<String,String> redisTemplate;


    @GetMapping("/delUser")
    public Result queryDelUser(@RequestParam("userId") int userId){

        int count= userService.querydelUser(userId);
        if (count > 0){
            return Result.success("删除成功!");
        }else{
            return Result.error("107","删除失败");
        }

    }

    @GetMapping("/allUser")
    public Result queryAllUser(){
        List<User> list= userService.queryAllUser();
        return Result.success(list);
    }

//    tongguoID查询User
    @GetMapping("/userById")
    public Result userById(@RequestParam("userId") int userId){
        User user= userService.userById(userId);
        return Result.success(user);
    }
//    查询此账号是否已经使用
    @GetMapping("/userByAccount")
    public Result userByAccount(@RequestParam("account") String account){
        int count= userService.userByAccount(account);
        if (count > 0){
            return Result.error("107","此账号已存在！");
        }else{
            return Result.success();
        }

    }
//    查询此电话是否已经使用
    @GetMapping("/userByPhone")
    public Result userByPhone(@RequestParam("userPhone") String userPhone){
        int count= userService.userByPhone(userPhone);
        if (count > 0){
            return Result.error("107","此手机号已被绑定！");
        }else{
            return Result.success();
        }

    }

//    通过名字查询用户
    @GetMapping("/userByName")
    public Result userByName(@RequestParam("userName") String userName){
        List<User> list= userService.queryUserByName(userName);
        return Result.success(list);
    }

//    注册
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        userService.register(user);
//        自动创建一个歌单
        Lists lists=new Lists();
        lists.setListTitle("我喜欢的音乐");
        lists.setListUserId(user.getUserId());
        lists.setListImg("http://localhost:8081/file/image/1393081239628722.jpg");
        lists.setListType(7);
        listsService.addLists(lists);
        return Result.success();
    }


//    登陆
    @GetMapping("/login")
    public Result login(@RequestParam("account") String account,
                        @RequestParam("password") String password,
                        HttpServletResponse response,HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        if("".equals(account) && "".equals(password)){
            Cookie[] cookies = request.getCookies();
            System.out.println("使用cookie自动登录");
            String cookieAccount="";
            String cookiePassword="";
            if(cookies !=null && cookies.length>0){
                for (Cookie cookie:cookies) {
                    System.out.println("遍历cookie："+ cookie.getName());
                    if ("account".equals(cookie.getName())) {
                        cookieAccount=cookie.getValue();
                    }else if("password".equals(cookie.getName())){
                        cookiePassword=cookie.getValue();
                    }
                }
                User user=userService.login(cookieAccount,cookiePassword);
                List<Song> songList = songService.querySongOfUser(user.getUserId());
                map.put("user",user);
                map.put("list",songList);
                System.out.println("自动登录成功");
                return Result.success("200","Cookie自动登录成功",map);
            }else{
                return Result.error("105","cookie已经过期，自动登录失败！");
            }
        }else{
            User user=userService.login(account,password);
            if(user!=null){
                List<Song> songList = songService.querySongOfUser(user.getUserId());
                map.put("user",user);
                map.put("list",songList);

                Cookie cookie1=new Cookie("account",account);
                cookie1.setMaxAge(60*60*24);
                response.addCookie(cookie1);
                Cookie cookie2=new Cookie("password",password);
                cookie2.setMaxAge(60*60*24);
                response.addCookie(cookie2);
                return Result.success(map);
            }else{
                return Result.error("104","用户名或密码错误");
            }
        }
    }

//登出
    @GetMapping("/logout")
    public Result logout(@RequestParam("account") String account,
                       @RequestParam("password") String password,
                       HttpServletResponse response){
        Cookie cookie1=new Cookie("account",account);
        cookie1.setMaxAge(0);
        response.addCookie(cookie1);
        Cookie cookie2=new Cookie("password",password);
        cookie2.setMaxAge(0);
        response.addCookie(cookie2);
        return Result.success();
    }

//    修改用户信息
    @PutMapping("/putUser")
    public Result putUser(@RequestBody User user){
        System.out.println(user);
        userService.putUser(user);
        return Result.success();
    }
}
