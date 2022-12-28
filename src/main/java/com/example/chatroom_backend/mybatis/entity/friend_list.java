package com.example.chatroom_backend.mybatis.entity;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
@Data
public class friend_list {
    private long id;
    private String owner;
    private user[] items;
    public Boolean exist(String strid)
    {
        int i=0;
        Boolean result=false;
        for(i=0;i<items.length;i++)
        {
            if(items[i].equals(strid))
            {
                result=true;
            }
        }
        return result;
    }
}
