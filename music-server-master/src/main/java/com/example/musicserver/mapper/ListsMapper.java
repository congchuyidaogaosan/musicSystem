package com.example.musicserver.mapper;

import com.example.musicserver.pojo.Lists;
import com.example.musicserver.pojo.Page;
import com.example.musicserver.pojo.Singer;
import com.example.musicserver.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ListsMapper {

    List<Lists> queryLists(@Param("startIndex") int startIndex,@Param("pageSize") int pageSize);

    List<Lists> queryListsByType(@Param("startIndex") int startIndex,@Param("pageSize")int pageSize,@Param("listType")int listType);

    List<Lists> topLists();

    List<Lists> queryListsByUserId(int userId);

    List<Lists> queryListByName(String listName);

    List<Lists> colListsByUserId(int userId);

    int isCol(@Param("listId") int listId,@Param("userId") int userId);

    void addCol(@Param("listId")int listId,@Param("userId")int userId);

    void delCol(@Param("listId") int listId,@Param("userId")int userId);

    int countOfList();

    int countOfListByType(@Param("listType")int listType);

    Lists userOfList(int listId);

    int addLists(Lists lists);

    void addSongToList(@Param("songId")int songId,@Param("listId")int listId);

    void delSongOfList(@Param("songId") int songId,@Param("listId")int listId);

    void addSongToMyList(@Param("songId")int songId,@Param("userId")int userId);

    void delSongToMyList(@Param("songId")int songId,@Param("userId")int userId);

    void putLists(Lists lists);

    void putListPlayNum(int listPlayNum,int listId);

    void delLists(int listId);
}
