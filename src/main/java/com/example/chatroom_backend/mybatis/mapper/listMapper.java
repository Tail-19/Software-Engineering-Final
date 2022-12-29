package com.example.chatroom_backend.mybatis.mapper;
import com.example.chatroom_backend.mybatis.entity.user;
import com.example.chatroom_backend.mybatis.entity.friend_list;
import org.apache.ibatis.annotations.*;

import java.time.LocalTime;
import java.util.List;
public interface listMapper {
    @Insert("insert into list(ownerID, ownerName,friendID,friendName) values (#{ownerID},#{ownerName},#{friendID},#{friendName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add_newlsit(friend_list flist);


    @Select("select * from list where ownerID = #{id} ")
    friend_list[] get_listbyid (String id);

    @Select("select * from list where ownerID = #{id1} and friendID= #{id2}")
    friend_list[] get_listbytwoid (String id1,String id2);

    @Delete("delete from list where ownerID = #{oid} and friendID=#{fid}")
    void deleteById(String oid,String fid);
}
