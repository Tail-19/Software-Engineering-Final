package com.example.software.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.software.mybatis.entity.pile;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Mapper
@Repository

public interface adminMapper extends BaseMapper<pile>{

    //更新充电桩状态
    @Update("update `piles` set `state` = #{state} where `id` = #{id}")
    void setState(@Param("id") int id, @Param("state") int state);

    //查看充电桩状态
    @Select("select `state` from `piles` where `id` = #{id}")
    int getState(@Param("id") int id);

    //查看充电桩所有信息
    @Select("select * from `piles` order by `id`")
    List<pile> getAllPiles();

    //查看单个充电桩所有信息
    @Select("select * from `piles` where `id` = #{id}")
    List<pile> getPile(@Param("id") int id);

}