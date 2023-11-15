package com.example.musicserver.mapper;

import com.example.musicserver.pojo.Fans;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FansMapper {

    List<Fans> getMyFocus(int userId);

    List<Fans> getMyFans(int userId);

    int getFocusCount(int userId);

    int getFansCount(int userId);

    int singerIsFocus(@Param("userId") int userId,@Param("singerId") int singerId);

    int userIsFocus(@Param("userId") int userId,@Param("myId") int myId);

    int userIsFans(@Param("userId") int userId,@Param("myId") int myId);

    void addFocus(@Param("userId") int userId,@Param("myId") int myId,@Param("type") int type);

    void delFocus(@Param("userId") int userId,@Param("myId") int myId,@Param("type") int type);
}
