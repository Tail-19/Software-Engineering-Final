package com.example.software;

import com.example.software.mybatis.entity.user;
import com.example.software.mybatis.entity.apply;
import com.example.software.mybatis.entity.apply_list;
import com.example.software.mybatis.entity.friend_list;
import com.example.software.mybatis.mapper.userMapper;
import com.example.software.mybatis.mapper.listMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("")
public class usercontroller {
    @Resource
    userMapper theUserMapper;
    apply_list waitlist=new apply_list();
    @PostMapping("/user/register")
    public Map<String, Object> register(@RequestBody Map<String,String> input){
        //System.out.println("用户名"+input.get("username"));
        /*user theUser = new user();
        theUser.setPassword(input.get("password"));
        theUser.setUsername(input.get("username"));*/
        Map<String, Object> result = new HashMap<>();
        if ((input.get("password").length()>=8&&input.get("username").length()!=0)&&theUserMapper.findByName(input.get("username"))==null){
            user theuser=new user(input.get("username"),input.get("password"),"user");
            theUserMapper.register(theuser);
            result.put("code",20000);
            result.put("message", "注册成功");
        }
        else{
            result.put("code",20003);
            result.put("message", "注册失败");
        }
        result.put("data",null);
        return result;
    }

    @PostMapping("/user/login")
    public Map<String, Object> login(@RequestBody Map<String,String> input){
        Map<String, Object> result = new HashMap<>();
        if (theUserMapper.login(input.get("username"), input.get("password")).size()!=0){
            result.put("code", 20000);
            result.put("message","success");
        }else{
            result.put("code", 20003);
            result.put("message","fail");
        }
        result.put("data",null);
        return result;
    }

    @PostMapping("/user/startCharging")
    public Map<String, Object> startcharge(@RequestBody Map<String,Object> input){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        apply app=new apply((int) input.get("id"),(String) input.get("mode"),(int) input.get("amount"));
        if (waitlist.add_apply(app))
        {
            result.put("code", 20000);
            result.put("message","success");
            user theuser=theUserMapper.findById((int) input.get("id"));
            System.out.println("id:"+theUserMapper.findById2((int) input.get("id")));
            data.put("id",theuser.getUserid());
            data.put("username",theuser.getUsername());
            data.put("password",theuser.getPassword());
            data.put("balance",theuser.getBalance());
            data.put("role",theuser.getUserrole());
            boolean f;
            if(theuser.getIscharging()==0)
            {
                f=false;
            }
            else
            {
                f=true;
            }
            data.put("isCharging",f);
            data.put("mode",(String) input.get("mode"));
            data.put("amount",(int) input.get("amount"));
        }
        else
        {
            result.put("code", 20003);
            result.put("message","fail");
            data=null;
        }
        result.put("data",data);
        return result;
    }
    @PutMapping("/user/modifyAmount")
    public Map<String, Object> modifyAmount(@RequestBody Map<String,Object> input){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        if (waitlist.find_apply_byid((int) input.get("id"))!=null)
        {
            apply app=new apply((int) input.get("id"),waitlist.find_apply_byid((int) input.get("id")).getMode(),(int) input.get("amount"));
            waitlist.change_apply(app);
            result.put("code", 20000);
            result.put("message","success");
            user theuser=theUserMapper.findById((int) input.get("id"));
            //System.out.println("id:"+theUserMapper.findById2((int) input.get("id")));
            apply user_apply=waitlist.find_apply_byid((int) input.get("id"));
            data.put("id",theuser.getUserid());
            data.put("username",theuser.getUsername());
            data.put("password",theuser.getPassword());
            data.put("balance",theuser.getBalance());
            data.put("role",theuser.getUserrole());
            boolean f;
            if(theuser.getIscharging()==0)
            {
                f=false;
            }
            else
            {
                f=true;
            }
            data.put("isCharging",f);
            data.put("mode",user_apply.getMode());
            data.put("amount",user_apply.getAmount());
        }
        else
        {
            result.put("code", 20003);
            result.put("message","fail");
            data=null;
        }
        result.put("data",data);
        return result;
    }

    @PutMapping("/user/modifyMode")
    public Map<String, Object> modifyMode(@RequestBody Map<String,Object> input){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        if (waitlist.find_apply_byid((int) input.get("id"))!=null)
        {
            apply app=new apply((int) input.get("id"),(String) input.get("mode"),waitlist.find_apply_byid((int) input.get("id")).getAmount());
            waitlist.change_apply(app);
            result.put("code", 20000);
            result.put("message","success");
            user theuser=theUserMapper.findById((int) input.get("id"));
            //System.out.println("id:"+theUserMapper.findById2((int) input.get("id")));
            apply user_apply=waitlist.find_apply_byid((int) input.get("id"));
            data.put("id",theuser.getUserid());
            data.put("username",theuser.getUsername());
            data.put("password",theuser.getPassword());
            data.put("balance",theuser.getBalance());
            data.put("role",theuser.getUserrole());
            boolean f;
            if(theuser.getIscharging()==0)
            {
                f=false;
            }
            else
            {
                f=true;
            }
            data.put("isCharging",f);
            data.put("mode",user_apply.getMode());
            data.put("amount",user_apply.getAmount());
        }
        else
        {
            result.put("code", 20003);
            result.put("message","fail");
            data=null;
        }
        result.put("data",data);
        return result;
    }

    @GetMapping("/user/all")
    public Map<String, Object> getall(@RequestBody Map<String,Object> input){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        //System.out.println(theUserMapper.findByName((String) input.get("username")));
        user theuser;
        if ((theuser=theUserMapper.findByName((String) input.get("username")))!=null)
        {
            result.put("code", 20000);
            result.put("message","success");
            data.put("id",theuser.getUserid());
            data.put("username",theuser.getUsername());
            data.put("password",theuser.getPassword());
            data.put("balance",theuser.getBalance());
            data.put("role",theuser.getUserrole());
            boolean f;
            if(theuser.getIscharging()==0)
            {
                f=false;
            }
            else
            {
                f=true;
            }
            data.put("isCharging",f);
            //System.out.println("userid:"+theuser.getUserid());
            apply user_apply=waitlist.find_apply_byid(theuser.getUserid());
            if(user_apply!=null)
            {
                data.put("mode",user_apply.getMode());
                data.put("amount",user_apply.getAmount());
            }
            else
            {
                data.put("mode",null);//如果未提交充电申请，则返回的这一部分为null
                data.put("amount",null);
            }
        }
        else
        {
            result.put("code", 20003);
            result.put("message","fail");
            data=null;
        }
        result.put("data",data);
        return result;
    }

    @PutMapping("/user/stopCharging")
    public Map<String, Object> stopCharging(@RequestBody Map<String,Object> input){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        apply user_apply;
        //System.out.println(theUserMapper.findByName((String) input.get("username")));
        if ((user_apply=waitlist.find_apply_byid((int) input.get("id")))!=null)
        {
            user theuser=theUserMapper.findById((int) input.get("id"));
            result.put("code", 20000);
            result.put("message","success");
            data.put("id",theuser.getUserid());
            data.put("username",theuser.getUsername());
            data.put("password",theuser.getPassword());
            data.put("balance",theuser.getBalance());
            data.put("role",theuser.getUserrole());
            boolean f;
            if(theuser.getIscharging()==0)
            {
                f=false;
            }
            else
            {
                f=true;
            }
            data.put("isCharging",f);
            //System.out.println("userid:"+theuser.getUserid());
            if(user_apply!=null)
            {
                data.put("mode",user_apply.getMode());
                data.put("amount",user_apply.getAmount());
            }
            else
            {
                data.put("mode",null);//如果未提交充电申请，则返回的这一部分为null
                data.put("amount",null);
            }
            waitlist.delete_apply_byid((int) input.get("id"));
        }
        else
        {
            result.put("code", 20003);
            result.put("message","fail");
            data=null;
        }
        result.put("data",data);
        return result;
    }

    @GetMapping("/areaQueue/spare")
    public Map<String, Object> spare(){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        result.put("code", 20000);
        result.put("message","success");
        data.put("people",waitlist.sparenum());
        result.put("data",data);
        return result;
    }

    @Bean
    public CommonsRequestLoggingFilter logFilter(){
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        return filter;
    }

}
