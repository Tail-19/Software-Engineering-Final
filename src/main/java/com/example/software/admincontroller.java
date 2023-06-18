package com.example.software;

import com.example.software.mybatis.entity.user;
import com.example.software.mybatis.entity.apply;
import com.example.software.mybatis.entity.apply_list;
import com.example.software.mybatis.entity.pile;
import com.example.software.mybatis.mapper.userMapper;
import com.example.software.mybatis.mapper.adminMapper;
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
@CrossOrigin(origins = "*",maxAge = 3600)
public class admincontroller {

    @Resource
    adminMapper theadminMapper;




    //判断改变状态是否合法
    /*
    private boolean isStateChangeAllowed(int currentState, int state) {
        if(currentState==0&&(state==2)) {return true;}  //空闲->故障
        if(currentState==1&&(state==2)) {return true;}  //充电中->故障
        if(currentState==2&&(state==0)) {return true;}  //故障->空闲

    }
    */

    //设置充电桩状态 传入id和state 0 空闲-free","1-充电中-working","2-故障-fault"，"3-关闭-closed"
    @PutMapping("/chargingAdmin/state")
    public Map<String, Object> set_pile(@RequestParam Map<String,String> input){
        Map<String, Object> result = new HashMap<>();
        int pileid= Integer.parseInt(input.get("id"));
        int status= Integer.parseInt(input.get("state"));
        int currentstate = theadminMapper.getState(pileid);

            theadminMapper.setState(pileid,status);
            result.put("code",20000);
            result.put("message","设置充电桩状态成功");
            result.put("data",null);
            //改变充电桩状态后 应有调度算法自动适配 还没写

        /*
        else{
            result.put("code",19999);
            result.put("message","设置充电桩状态失败");
            result.put("data",null);
        }
        */
        return result;
    }

    //查看充电桩报单
    @GetMapping("/chargingPile/list")
    public Map<String, Object> get_pile(@RequestParam Map<String,String> input){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        if (!input.containsKey("id") || input.get("id") == null || input.get("id").isEmpty())
        {
            List<pile> piles=theadminMapper.getAllPiles();
            data.put("total",piles.size());
            data.put("rows",piles);
            result.put("code",20000);
            result.put("message","success");
            result.put("data",data);
            return result;
        }
        else {
            int pileid= Integer.parseInt(input.get("id"));
            List<pile> pile = theadminMapper.getPile(pileid);
            data.put("total",1);
            data.put("rows",pile);
            result.put("code",20000);
            result.put("message","success");
            result.put("data",data);
            return result;
        }

    }


    //查询充电桩状态
    @GetMapping("/chargingPile/state")
    public Map<String, Object> state(@RequestParam("id") String id){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        int pileid= Integer.parseInt(id);;
        int state = theadminMapper.getState(pileid);
        data.put("state",state);
        result.put("code",20000);
        result.put("message","查询充电桩状态成功");
        result.put("data",data);
        return result;
    }





}