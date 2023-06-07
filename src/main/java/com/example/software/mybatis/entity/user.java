package com.example.software.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class user {
    @TableId(type = IdType.AUTO)
    private String user_id;
    private String username;
    private String password;
    private String role;//"user"为普通用户，"manager"为管理员
}
