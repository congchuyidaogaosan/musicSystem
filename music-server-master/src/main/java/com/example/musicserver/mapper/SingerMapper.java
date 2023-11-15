package com.example.musicserver.mapper;

import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.Singer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SingerMapper {

    List<Singer> queryAllSinger(@Param("startIndex")int startIndex,@Param("pageSize") int pageSize);

    List<Singer> topSinger();

    List<Singer> querySingerByType(@Param("startIndex")int startIndex,@Param("pageSize") int pageSize,@Param("singerType")int singerType);

    Singer querySingerById(int singerId);

    int countOfSinger();

    int countOfSingerByType(@Param("singerType")int singerType);

    void addSinger(String singer);

    void putSinger(Singer singer);

    void delSinger(int singerId);

    List<Singer> querySingerByName(String singerName);

    int SingerByName(String singerName);

    Singer IdSingerByName (String singerName);
}
