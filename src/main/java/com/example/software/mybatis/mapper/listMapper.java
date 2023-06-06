package com.example.software.mybatis.mapper;
import com.example.software.mybatis.entity.user;
import com.example.software.mybatis.entity.friend_list;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
@Mapper
@Repository
public interface listMapper {
    @Insert("insert into list(ownerID, ownerName,friendID,friendName) values (#{ownerID},#{ownerName},#{friendID},#{friendName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add_newlsit(friend_list flist);

    @Insert("insert into list(ownerID, ownerName,friendID,friendName) values (#{ownerID},#{ownerName},#{friendID},#{friendName})")
    @Options(useGeneratedKeys = true)
    int add_list(String ownerID, String ownerName, String friendID,String friendName);


    @Results({
            @Result(property = "ownerID", column = "ownerID"),
            @Result(property = "ownerName", column = "ownerName"),
            @Result(property = "friendID", column = "friendID"),
            @Result(property = "friendName", column = "friendName")
    })
    @Select("select * from list where ownerID = #{id} ")
    friend_list[] get_listbyid (String id);

    @Select("select * from list where ownerID = #{id1} and friendID= #{id2}")
    friend_list get_listbytwoid (String id1,String id2);

    @Delete("delete from list where ownerID = #{oid} and friendID=#{fid}")
    void deleteById(String oid,String fid);
}