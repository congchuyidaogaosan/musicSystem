package com.example.musicserver.service.impl;

import com.example.musicserver.mapper.CommentsMapper;
import com.example.musicserver.pojo.Comments;
import com.example.musicserver.pojo.Page;
import com.example.musicserver.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    CommentsMapper commentsMapper;

    @Override
    public List<Comments> queryComments(int commTargetId,int commType) {
        return commentsMapper.queryComments(commTargetId, commType);
    }

    @Override
    public List<Comments> AllComm() {
        return commentsMapper.AllComm();
    }

    @Override
    public int countOfComments(int commType) {
        return commentsMapper.countOfComments(commType);
    }

    @Override
    public void addComm(Comments comments) {
        commentsMapper.addComm(comments);
    }

    @Override
    public void delComm(int commId) {
        commentsMapper.delComm(commId);
    }

    @Override
    public void addLikeComm(int commId, int userId) {
        commentsMapper.addLikeComm(commId, userId);
    }

    @Override
    public void delLikeComm(int commId, int userId) {
        commentsMapper.delLikeComm(commId, userId);
    }


}
