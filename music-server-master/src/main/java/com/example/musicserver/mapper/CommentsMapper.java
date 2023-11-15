package com.example.musicserver.mapper;

import com.example.musicserver.pojo.Comments;
import com.example.musicserver.pojo.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentsMapper {

    List<Comments> queryComments(@Param("commTargetId") int commTargetId,@Param("commType") int commType);

    int countOfComments(@Param("commType")int commType);

    void addComm(Comments comments);

    void delComm(@Param("commId") int commId);

    List<Comments> AllComm();
    void addLikeComm(@Param("commId")int commId,@Param("userId") int userId);

    void delLikeComm(@Param("commId")int commId,@Param("userId") int userId);
}
