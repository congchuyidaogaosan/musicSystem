package com.example.musicserver.mapper;

import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.Slide;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SlideMapper {

    List<Slide> showSlide();


//    int countOfSlide();

    void addSlide(Slide slide);

    void putSlide(Slide slide);

    void delSlide(int slideId);
}
