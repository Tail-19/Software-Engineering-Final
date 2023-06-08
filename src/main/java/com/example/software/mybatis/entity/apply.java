package com.example.software.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class apply {
    public int userid;
    public String mode;//"fast"和"slow"两种
    public int amount;//充电量
    public int queue_order;//排队号码
    public apply(int userid, String m, int am)
    {
        this.userid=userid;
        this.mode=m;
        this.amount=am;
    }
}
