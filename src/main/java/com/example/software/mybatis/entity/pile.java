package com.example.software.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalTime;

@Data
public class pile {     //充电桩

    //充电桩编号
    private int id;

    //正在充电的用户ID
    private int chargingId;

    //等待充电的用户ID
    private int waitId;

    //充电桩种类，分为"slow"和"fast"
    private String type;

    //充电桩状态，分为"0-停止工作","1-正在充电","2-故障"
    private int state;

    //总充电次数
    private int chargingNumber;

    //总充电时长
    private int chargingTime;

    //总充电电量
    private double chargingAmount;

    //总充电费用
    private double chargingCost;

    //总服务费
    private double serviceCost;

    //总费用（充电+服务）
    private double totalCost;

    private double waitAmount;

    private int leftTime;
}




