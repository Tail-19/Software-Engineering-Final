package com.example.chatroom_backend.mybatis.entity;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
@Data
public class friend_list {
    private long id;
    private String ownerID;
    private String ownerName;
    private String friendID;
    private String friendName;
}
