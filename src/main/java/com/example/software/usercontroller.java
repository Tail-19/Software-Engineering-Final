package com.example.software;

import com.example.software.mybatis.entity.user;
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
@RequestMapping("/user")
public class usercontroller {
    @Resource
    userMapper theUserMapper;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String,String> input){
        //System.out.println("用户名"+input.get("username"));
        /*user theUser = new user();
        theUser.setPassword(input.get("password"));
        theUser.setUsername(input.get("username"));*/
        Map<String, Object> result = new HashMap<>();
        if ((input.get("password").length()>=8&&input.get("username").length()!=0)&&theUserMapper.findByName(input.get("username"))==null){
            theUserMapper.register(input.get("username"),input.get("password"));
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

    @PostMapping("/login")
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
