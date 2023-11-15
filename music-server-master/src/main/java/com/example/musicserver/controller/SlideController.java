package com.example.musicserver.controller;

import com.example.musicserver.pojo.Slide;
import com.example.musicserver.service.impl.SlideServiceImpl;
import com.example.musicserver.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slide")
public class SlideController {

    @Autowired
    SlideServiceImpl slideService;

    //轮播图
    @GetMapping("/showSlide")
    public Result showSlide(){
        List<Slide> slides = slideService.showSlide();
        return Result.success(slides);
    }

    @PostMapping("/addSlide")
    public Result addSlide(@RequestBody Slide slide){
        slideService.addSlide(slide);
        return Result.success();
    }

    @PutMapping("/putSlide")
    public Result putSlide(@RequestBody Slide slide){
        slideService.putSlide(slide);
        return Result.success();
    }

    @DeleteMapping("/delSlide/{id}")
    public Result delSlide(@PathVariable int id){
        slideService.delSlide(id);
        return Result.success();
    }
}
