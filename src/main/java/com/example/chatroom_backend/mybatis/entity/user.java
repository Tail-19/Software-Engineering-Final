package com.example.chatroom_backend.mybatis.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class user {
    private String userName;
    private String password;
    private String pictureURL;
}
