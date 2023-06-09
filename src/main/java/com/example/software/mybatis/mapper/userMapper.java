package com.example.software.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.software.mybatis.entity.user;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface userMapper extends BaseMapper<user> {
    @Insert("insert into `users`(`username`, `password`,`userrole`,`balance`,`ischarging`) values (#{theuser.username},#{theuser.password},'user',0,0)")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    void register(@Param("theuser") user theuser);

    @Select("select * from `users` where `username` = #{userName}")
    user findByName( String userName);

    @Select("select * from `users` where `username` = #{username} and `password` = #{password}")
    List<user> login(String username, String password);

    @Select("select * from `users` where `userid` = #{id}")
    user findById(int id);

    @Select("select userid from `users` where `userid` = #{id}")
    int findById2(int id);

    @Update("update `users` set `ischarging`=1 where `userid`=#{userid}}")
    void start_charge(@Param("userid") int userid);

    @Update("update `users` set `ischarging`=0 where `userid`=#{userid}}")
    void stop_charge(@Param("userid") int userid);


}
/*public interface userMapper extends BaseMapper<user> {
    @Insert("insert into users(userName, password, pictureURL) values (#{theUser.userName},#{theUser.password},#{theUser.pictureURL})")
    @Options(useGeneratedKeys = true, keyProperty = "theUser.userId")
    void register(@Param("theUser") user theUser);

    @Update("update users set userName = #{userName}, password = #{password} where userId = #{id}")
    void change_info(@Param("userName") String userName,@Param("password") String password, @Param("id") String id);

    @Update("update users set pictureURL = #{pictureURL} where userId = #{id}")
    void uploadPicture(@Param("pictureURL") String pictureURL, @Param("id") String id);

    @Select("select pictureURL from users where userId = #{userId}")
    String getPictrue(String userId);

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
    @Select("select * from users where userId = #{userId} and password = #{password}")
    List<user> login(int userId, String password);

    @Results({
            @Result(property = "userName", column = "userName"),
            @Result(property = "password", column = "password"),
            @Result(property = "pictureURL", column = "pictureURL")
    })
    @Select("select * from users where userName = #{userName} ")
    user findByName (String userName);

    @Insert("insert into msgs(receiveUserId, content, senderUserId, time) values (#{receiveUserId},#{content}, #{senderUserId}, #{time})")
    void send_message(@Param("receiveUserId") String receiveUserId, @Param("content") String content, @Param("senderUserId") String senderUserId, @Param("time") LocalTime time);


    @Select("select receiveUserId, content, senderUserId from msgs where (receiveUserId = #{receiverId} and senderUserId = #{senderUserId}) or (receiveUserId = #{senderUserId} and senderUserId = #{receiverId}) order by time asc")
    List<Map<String,String>> get_message(String senderUserId, String receiverId);
}*/
