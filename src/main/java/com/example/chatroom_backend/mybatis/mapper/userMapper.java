package com.example.chatroom_backend.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.chatroom_backend.mybatis.entity.user;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Mapper
@Repository
public interface userMapper extends BaseMapper<user> {
    @Insert("insert into users(userName, password, pictureURL) values (#{theUser.userName},#{theUser.password},#{theUser.pictureURL})")
    @Options(useGeneratedKeys = true, keyProperty = "theUser.userId")
    void register(@Param("theUser") user theUser);

    @Update("update users set userName = #{userName}, password = #{password} where userId = #{id}")
    void change_info(@Param("userName") String userName,@Param("password") String password, @Param("id") String id);

    @Update("update users set pictureURL = #{pictureURL} where userId = #{id}")
    void uploadPicture(@Param("pictureURL") String pictureURL, @Param("id") String id);

    @Results({
            @Result(property = "userName", column = "userName"),
            @Result(property = "password", column = "password"),
            @Result(property = "pictureURL", column = "pictureURL")
    })
    @Select("select * from users where userId = #{id} ")
    user search (String id);

    @Results({
            @Result(property = "userName", column = "userName"),
            @Result(property = "password", column = "password"),
            @Result(property = "pictureURL", column = "pictureURL")
    })
    @Select("select * from users where userName = #{userName} and password = #{password}")
    List<user> login(String userName, String password);

    @Results({
            @Result(property = "userName", column = "userName"),
            @Result(property = "password", column = "password"),
            @Result(property = "pictureURL", column = "pictureURL")
    })
    @Select("select * from users where userName = #{userName} ")
    user findByName (String userName);

    @Insert("insert into msgs(receiveUserId, content, senderUserId, time) values (#{receiveUserId},#{content}, #{senderUserId}, #{time})")
    void send_message(@Param("receiveUserId") String receiveUserId, @Param("content") String content, @Param("senderUserId") String senderUserId, @Param("time") LocalTime time);


    @Select("select content from msgs where receiveUserId = (#{receiverId}) and senderUserId = #{senderUserId} order by time asc")
    List<String> get_message(String senderUserId, String receiverId);
}
