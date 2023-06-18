package com.example.software.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.software.mybatis.entity.pile;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository

public interface adminMapper extends BaseMapper<pile>{

    //更新充电桩状态
    @Update("update `piles` set `state` = #{state} where `id` = #{id}")
    void setState(@Param("id") int id, @Param("state") int state);

    //查看充电桩状态
    @Select("select `state` from `piles` where `id` = #{id}")
    int getState(@Param("id") int id);

    //查看充电桩类型
    @Select("select `type` from `piles` where `id` = #{id}")
    String getType(@Param("id") int id);

    //查看充电桩所有信息
    @Select("select * from `piles` order by `id`")
    List<pile> getAllPiles();

    //查看单个充电桩所有信息
    @Select("select * from `piles` where `id` = #{id}")
    List<pile> getPile(@Param("id") int id);

    //查看等待队列id
    @Select("select `wait_id` from `piles` where `id` = #{id}")
    String getWaitid(@Param("id") int id);

    @Override
    @Update("update `piles` set `state` = #{pile.id},`charging_id` = #{pile.chargingId},`wait_id` = #{pile.waitId},`charging_number` = #{pile.chargingNumber}," +
            "`charging_time` = #{pile.chargingTime},`charging_amount` = #{pile.chargingAmount},`charging_cost` = #{pile.chargingCost},`service_cost` = #{pile.serviceCost}," +
            "`total_cost` = #{pile.totalCost},`wait_amount` = #{pile.waitAmount},`left_time` = #{pile.leftTime},  where `id` = #{pile.id}")
    int updateById(@Param("pile") pile pile);


}