package com.example.chatroom_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.chatroom_backend.mybatis.mapper")
public class ChatroomBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatroomBackendApplication.class, args);
    }

}
