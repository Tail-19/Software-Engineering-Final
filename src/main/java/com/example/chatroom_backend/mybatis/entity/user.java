package com.example.chatroom_backend.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class user {
    @TableId(type = IdType.AUTO)
    private String userId;
    private String userName;
    private String password;
    private String pictureURL;

}
