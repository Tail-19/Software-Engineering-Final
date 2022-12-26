package com.example.chatroom_backend.mybatis.mapper;

import com.example.chatroom_backend.mybatis.entity.user;
import org.apache.ibatis.annotations.*;

import java.time.LocalTime;
import java.util.List;

public interface userMapper {
    @Insert("insert into users(userName, password, picture) values (#{userName},#{password},#{pictureURL})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int register(@Param("userName") String userName, @Param("password") String password, @Param("pictureURL") String pictureURL);

    @Update("update users set userName = #{userName}, password = #{password} where userId = #{id}")
    int change_info(@Param("userName") String userName,@Param("password") String password, @Param("id") String id);

    @Results({
            @Result(property = "userName", column = "userName"),
            @Result(property = "password", column = "password"),
            @Result(property = "pictureURL", column = "pictureURL")
    })
    @Select("select * from users where userId = (#{id}) ")
    user search (String id);

    @Results({
            @Result(property = "userName", column = "userName"),
            @Result(property = "password", column = "password"),
            @Result(property = "pictureURL", column = "pictureURL")
    })
    @Select("select * from users where userName = (#{userName}), password = (#{password})")
    List<user> login(@Param("userName") String userName, @Param("password") String password);

    @Insert("insert into msgs(userId, content, time) values (#{userId},#{content}, #{time})")
    void send_message(@Param("userId") String userId, @Param("content") String content, @Param("time") LocalTime time);

    @Select("select content from msgs where userId = (#{userId}) order by time asc")
    List<String> get_message(String userId);
}
