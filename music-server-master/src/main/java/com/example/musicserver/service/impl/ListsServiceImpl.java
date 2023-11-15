package com.example.musicserver.service.impl;

import com.example.musicserver.mapper.ListsMapper;
import com.example.musicserver.pojo.Lists;

import com.example.musicserver.service.ListsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ListsServiceImpl implements ListsService {

    @Autowired
    ListsMapper listsMapper;
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public int isCol(int listId, int userId) {
        return listsMapper.isCol(listId, userId);
    }

    @Override
    public void addCol(int listId, int userId) {
        listsMapper.addCol(listId, userId);
    }

    @Override
    public void delCol(int listId, int userId) {
        listsMapper.delCol(listId, userId);
    }

    @Override
    public List<Lists> queryLists(int startIndex,int pageSize) {
        return listsMapper.queryLists(startIndex, pageSize);
    }

    @Override
    public List<Lists> queryListsByType(int startIndex, int pageSize, int listType) {
        return listsMapper.queryListsByType(startIndex, pageSize, listType);
    }

    @Override
    public List<Lists> queryListByName(String listName) {
        return listsMapper.queryListByName(listName);
    }

    @Override
    public List<Lists> topLists() {
        return listsMapper.topLists();
    }

    @Override
    public List<Lists> colListsByUserId(int userId) {
        return listsMapper.colListsByUserId(userId);
    }

    @Override
    public void addSongToList(int songId, int listId) {
         listsMapper.addSongToList(songId, listId);
    }

    @Override
    public void delSongOfList(int songId, int listId) {
        listsMapper.delSongOfList(songId, listId);
    }

    @Override
    public void delSongToMyList(int songId, int userId) {
        listsMapper.delSongToMyList(songId, userId);
    }

    @Override
    public void addSongToMyList(int songId, int userId) {
        listsMapper.addSongToMyList(songId, userId);
    }

    @Override
    public List<Lists> queryListsByUserId(int userId) {
        return listsMapper.queryListsByUserId(userId);
    }

    @Override
    public int countOfList() {
        return listsMapper.countOfList();
    }

    @Override
    public int countOfListByType(int listType) {
        return listsMapper.countOfListByType(listType);
    }

    @Override
    public Lists userOfList(int listId) {
        return listsMapper.userOfList(listId);
    }

    @Override
    public int addLists(Lists lists) {
        return listsMapper.addLists(lists);
    }

    @Override
    public void putLists(Lists lists) {
        listsMapper.putLists(lists);
    }

    @Override
    public void delLists(int listId) {
        listsMapper.delLists(listId);
    }


    @Scheduled(cron ="0 0/5 * * * ?")
    public void updatePlayNum(){
        List<Lists> lists=listsMapper.queryLists(0,1000);
        for (Lists list : lists) {
            String key="list_"+list.getListId();
            int listPlayNum=Integer.parseInt(redisTemplate.opsForValue().get(key));
            listsMapper.putListPlayNum(listPlayNum,list.getListId());
        }
    }
}
