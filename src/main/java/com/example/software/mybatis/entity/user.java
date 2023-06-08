package com.example.software.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class user {
    @TableId(type = IdType.AUTO)
    private int userid;
    private String username;
    private String password;
    private String userrole;//"user"为普通用户，"admin"为管理员
    private int balance;//余额
    private int ischarging;//是否正在充电,0是未充电，1是充电
    public user(String name,String pwd,String role)
    {
        this.username=name;
        this.password=pwd;
        this.userrole=role;
        this.balance=0;
        this.ischarging=0;
    }
    public boolean startcharge()
    {
        if(this.ischarging==0)
        {
            this.ischarging=1;
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean stopcharge()
    {
        if(this.ischarging==1)
        {
            this.ischarging=0;
            return true;
        }
        else
        {
            return false;
        }
    }
}
