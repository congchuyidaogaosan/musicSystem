package com.example.musicserver.service.impl;

import com.example.musicserver.mapper.SlideMapper;
import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.Slide;
import com.example.musicserver.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    SlideMapper slideMapper;

    @Override
    public List<Slide> showSlide() {
        return slideMapper.showSlide();
    }

//    @Override
//    public int countOfSlide() {
//        return slideMapper.countOfSlide();
//    }

    @Override
    public void addSlide(Slide slide) {
        slideMapper.addSlide(slide);
    }

    @Override
    public void putSlide(Slide slide) {
        slideMapper.putSlide(slide);
    }

    @Override
    public void delSlide(int slideId) {
        slideMapper.delSlide(slideId);
    }
}
