package com.example.musicserver.service;

import com.example.musicserver.pojo.Lists;
import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.Song;

import java.util.List;

public interface ListsService {

    List<Lists> queryLists(int startIndex,int pageSize);

    List<Lists> queryListsByType(int startIndex,int pageSize,int listType);

    List<Lists> topLists();

    List<Lists> queryListsByUserId(int userId);

    List<Lists> queryListByName(String listName);

    List<Lists> colListsByUserId(int userId);

    int isCol(int listId,int userId);

    void addCol(int listId,int userId);

    void delCol(int listId,int userId);

    int countOfList();

    int countOfListByType(int listType);

    Lists userOfList(int listId);

    void addSongToList(int songId,int listId);

    void delSongOfList(int songId,int listId);

    void addSongToMyList(int songId,int userId);

    void delSongToMyList(int songId,int userId);

    int addLists(Lists lists);

    void putLists(Lists lists);

    void delLists(int listId);
}
