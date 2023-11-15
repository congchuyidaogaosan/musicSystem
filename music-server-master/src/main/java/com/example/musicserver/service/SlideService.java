package com.example.musicserver.service;

import com.example.musicserver.pojo.Slide;
import java.util.List;

public interface SlideService {

    List<Slide> showSlide();

//    int countOfSlide();

    void addSlide(Slide slide);

    void putSlide(Slide slide);

    void delSlide(int slideId);
}
