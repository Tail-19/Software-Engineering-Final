package com.example.chatroom_backend.mybatis.mapper;

import com.example.chatroom_backend.mybatis.entity.user;
import org.apache.ibatis.annotations.*;

public interface userMapper {
    @Insert("insert into users(userName, password, picture) values (#{userName},#{password},#{pictureURL})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int register(@Param("user") user theUser);

    @Update("update users set userName = #{userName}, password = #{password} where userId = #{id}")
    int change_info(String userName,String password, String id);

    @Select("select * from users where userId = (#{id}) ")
    user search (String id);

    @Select("select * from users where userName = (#{userName}), password = (#{password})")
    user login(String userName, String password);
}
