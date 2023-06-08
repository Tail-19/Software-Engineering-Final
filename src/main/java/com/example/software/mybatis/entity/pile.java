package com.example.software.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class pile {
    private int pile_id;
    private String type;//充电桩种类，分为"slow"和"fast"
    private String state;//充电桩状态，分为"open","close","fail"
}
