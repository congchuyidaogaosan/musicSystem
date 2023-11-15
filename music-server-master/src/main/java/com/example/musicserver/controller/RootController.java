package com.example.musicserver.controller;

import com.example.musicserver.pojo.Root;
import com.example.musicserver.service.impl.RootServiceImpl;
import com.example.musicserver.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/root")
public class RootController {
    @Autowired
    RootServiceImpl rootService;

    @GetMapping("/login")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        Root root = rootService.login(username,password);
        return Result.success("登陆成功");
    }


}
