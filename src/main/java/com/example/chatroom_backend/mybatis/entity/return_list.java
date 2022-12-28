package com.example.chatroom_backend.mybatis.entity;

public class return_list {
    private String id;
    private String username;
    public return_list(String friendid, String friendname)
    {
        id=friendid;
        username=friendname;
    }
}
