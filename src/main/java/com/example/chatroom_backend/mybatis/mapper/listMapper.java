package com.example.chatroom_backend.mybatis.mapper;
import com.example.chatroom_backend.mybatis.entity.user;
import com.example.chatroom_backend.mybatis.entity.friend_list;
import org.apache.ibatis.annotations.*;

import java.time.LocalTime;
import java.util.List;
public interface listMapper {
    @Insert("insert into list(owner, items) values (#{owner},#{items})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add_newlsit(friend_list flist);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "owner", column = "owner"),
            @Result(property = "items", column = "items")
    })
    @Select("select * from list where owner = (#{own}) ")
    friend_list get_listbyowner (String own);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "owner", column = "owner"),
            @Result(property = "items", column = "items")
    })
    @Select("select * from list where id = (#{id}) ")
    friend_list get_listbyid (int id);

    @Update("update todo set items = #{items} where id = #{id}")
    void update_list(friend_list flist);
}
