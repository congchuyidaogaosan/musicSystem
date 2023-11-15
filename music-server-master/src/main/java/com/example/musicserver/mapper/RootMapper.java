package com.example.musicserver.mapper;

import com.example.musicserver.pojo.Root;
import com.example.musicserver.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RootMapper {
    Root login(@Param("username") String username, @Param("password") String password);
}
