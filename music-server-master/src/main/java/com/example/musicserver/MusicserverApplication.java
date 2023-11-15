package com.example.musicserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.example.musicserver.mapper")
@EnableScheduling
public class MusicserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicserverApplication.class, args);
    }

}
