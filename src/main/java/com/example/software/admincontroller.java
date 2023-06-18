package com.example.software;

import com.example.software.mybatis.entity.user;
import com.example.software.mybatis.entity.apply;
import com.example.software.mybatis.entity.apply_list;
import com.example.software.mybatis.entity.pile;
import com.example.software.mybatis.mapper.userMapper;
import com.example.software.mybatis.mapper.adminMapper;
import com.example.software.usercontroller;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
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
import java.util.*;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "*",maxAge = 3600)
public class admincontroller implements ApplicationListener<ContextRefreshedEvent> {

    LocalTime myTime = LocalTime.of(5, 55, 0);
    double curFee = 0.4;
    @Resource
    adminMapper theadminMapper;
    public  apply_list Applylist= new apply_list();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {//保证只执行一次
            //需要执行的方法
            start();
        }
    }

    //判断改变状态是否合法
    /*
    private boolean isStateChangeAllowed(int currentState, int state) {
        if(currentState==0&&(state==2)) {return true;}  //空闲->故障
        if(currentState==1&&(state==2)) {return true;}  //充电中->故障
        if(currentState==2&&(state==0)) {return true;}  //故障->空闲

    }
    */

    //设置充电桩状态 传入id和state 0 空闲-free","1-充电中-working","2-故障-fault"，"3-关闭-closed"
    @PutMapping("/chargingPile/state")
    public Map<String, Object> set_pile(@RequestParam Map<String,String> input){
        Map<String, Object> result = new HashMap<>();
        int pileid= Integer.parseInt(input.get("id"));
        int status= Integer.parseInt(input.get("state"));
        int currentstate = theadminMapper.getState(pileid);

            theadminMapper.setState(pileid,status);
            result.put("code",20000);
            result.put("message","设置充电桩状态成功");
            result.put("data",null);
            pile tmpPile = theadminMapper.getPile(pileid).get(0);
            tmpPile.setChargingId(0);
            tmpPile.setLeftTime(0);
            tmpPile.setWaitId(0);
            tmpPile.setWaitAmount(0);
            theadminMapper.updateById(tmpPile.getId(),tmpPile.getState(),tmpPile.getChargingId(),tmpPile.getWaitId(),tmpPile.getChargingNumber(),
                tmpPile.getChargingTime(),tmpPile.getChargingAmount(),tmpPile.getChargingCost(),tmpPile.getServiceCost(),
                tmpPile.getTotalCost(),tmpPile.getWaitAmount(),tmpPile.getLeftTime());

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

    public void getUsers(){
        List<pile> piles = theadminMapper.getAllPiles();
        for (int i=0;i<piles.size();i++){
            if (piles.get(i).getState()==0){
                if (piles.get(i).getWaitId()!=0&&piles.get(i).getWaitAmount()!=0){
                    pile tmpPile = new pile();
                    tmpPile = theadminMapper.getPile(i+1).get(0);
                    tmpPile.setState(1);
                    tmpPile.setChargingNumber(tmpPile.getChargingNumber()+1);
                    tmpPile.setChargingId(tmpPile.getWaitId());
                    if (tmpPile.getType()=="fast"){
                        tmpPile.setLeftTime((int)tmpPile.getWaitAmount()*60/30);
                    }else{
                        tmpPile.setLeftTime((int)tmpPile.getWaitAmount()*60/7);
                    }
                    tmpPile.setWaitAmount(0);
                    tmpPile.setWaitId(0);
                    theadminMapper.updateById(tmpPile.getId(),tmpPile.getState(),tmpPile.getChargingId(),tmpPile.getWaitId(),tmpPile.getChargingNumber(),
                            tmpPile.getChargingTime(),tmpPile.getChargingAmount(),tmpPile.getChargingCost(),tmpPile.getServiceCost(),
                            tmpPile.getTotalCost(),tmpPile.getWaitAmount(),tmpPile.getLeftTime());
                    continue;
                }
                for (int j=0;j<usercontroller.waitlist.applylist.size();j++){
                    if (Objects.equals(usercontroller.waitlist.applylist.get(j).getMode(), piles.get(i).getType())){
                        pile tmpPile = new pile();
                        tmpPile = theadminMapper.getPile(i+1).get(0);
                        tmpPile.setState(1);
                        tmpPile.setChargingNumber(tmpPile.getChargingNumber()+1);
                        tmpPile.setChargingId(usercontroller.waitlist.applylist.get(j).userid);
                        if (tmpPile.getType()=="fast"){
                            tmpPile.setLeftTime(usercontroller.waitlist.applylist.get(j).getAmount()*60/30);
                        }else{
                            tmpPile.setLeftTime(usercontroller.waitlist.applylist.get(j).getAmount()*60/7);
                        }
                        tmpPile.setWaitAmount(0);
                        theadminMapper.updateById(tmpPile.getId(),tmpPile.getState(),tmpPile.getChargingId(),tmpPile.getWaitId(),tmpPile.getChargingNumber(),
                                tmpPile.getChargingTime(),tmpPile.getChargingAmount(),tmpPile.getChargingCost(),tmpPile.getServiceCost(),
                                tmpPile.getTotalCost(),tmpPile.getWaitAmount(),tmpPile.getLeftTime());
                        usercontroller.waitlist.delete_apply_byindex(j);
                        break;
                    }
                }
            }
        }
        for (int i=0;i<piles.size();i++){
            if (piles.get(i).getState()==1&&piles.get(i).getWaitId() == 0){
                for (int j=0;j<usercontroller.waitlist.applylist.size();j++){
                    if (Objects.equals(usercontroller.waitlist.applylist.get(j).getMode(), piles.get(i).getType())){
                        pile tmpPile = new pile();
                        tmpPile = theadminMapper.getPile(i+1).get(0);
                        tmpPile.setWaitId(usercontroller.waitlist.applylist.get(j).userid);
                        tmpPile.setWaitAmount(usercontroller.waitlist.applylist.get(j).getAmount());
                        theadminMapper.updateById(tmpPile.getId(),tmpPile.getState(),tmpPile.getChargingId(),tmpPile.getWaitId(),tmpPile.getChargingNumber(),
                                tmpPile.getChargingTime(),tmpPile.getChargingAmount(),tmpPile.getChargingCost(),tmpPile.getServiceCost(),
                                tmpPile.getTotalCost(),tmpPile.getWaitAmount(),tmpPile.getLeftTime());
                        usercontroller.waitlist.delete_apply_byindex(j);
                    }
                    break;
                }
            }
        }
    }

    public void calFee(){
        List<pile> piles = theadminMapper.getAllPiles();
        for (int i=0;i<piles.size();i++){
            if (piles.get(i).getState()==1){
                pile tmpPile = piles.get(i);
                tmpPile.setChargingTime(tmpPile.getChargingTime()+1);
                double tmpTotal = 0;
                if (tmpPile.getType().equals("slow")){
                    tmpPile.setChargingCost(tmpPile.getChargingCost()+curFee*7/60);
                    tmpPile.setServiceCost(tmpPile.getServiceCost()+0.8*7/60);
                    tmpTotal = curFee*7/60+0.8*7/60;
                }else{
                    tmpPile.setChargingCost(tmpPile.getChargingCost()+curFee*30/60);
                    tmpPile.setServiceCost(tmpPile.getServiceCost()+0.8*30/60);
                    tmpTotal = curFee*30/60+0.8*30/60;
                }
                tmpPile.setTotalCost(tmpPile.getTotalCost()+tmpTotal);
                theadminMapper.updateById(tmpPile.getId(),tmpPile.getState(),tmpPile.getChargingId(),tmpPile.getWaitId(),tmpPile.getChargingNumber(),
                        tmpPile.getChargingTime(),tmpPile.getChargingAmount(),tmpPile.getChargingCost(),tmpPile.getServiceCost(),
                        tmpPile.getTotalCost(),tmpPile.getWaitAmount(),tmpPile.getLeftTime());
            }
        }
    }

    public void calTimePri(){
        myTime = myTime.plusMinutes(1);
        if (myTime.isAfter(LocalTime.of(23,0,0))&& myTime.isBefore(LocalTime.of(7,0,0))){
            curFee=0.7;
        }else if(myTime.isAfter(LocalTime.of(10,0,0))&& myTime.isBefore(LocalTime.of(15,0,0))){
            curFee=1.0;
        }else{
            curFee=0.4;
        }
    }

    public void checkEnd(){
        List<pile> piles = theadminMapper.getAllPiles();
        for (int i=0;i<piles.size();i++){
            if (piles.get(i).getState()==1){
                piles.get(i).setLeftTime(piles.get(i).getLeftTime()-1);
                if (piles.get(i).getLeftTime()==0){
                    piles.get(i).setChargingId(0);
                    piles.get(i).setState(0);
                }
                if (piles.get(i).getType()=="fast"){
                    piles.get(i).setChargingAmount(piles.get(i).getChargingAmount()+(double)30/60);
                }else{
                    piles.get(i).setChargingAmount(piles.get(i).getChargingAmount()+(double)7/60);
                }
                theadminMapper.updateById(piles.get(i).getId(),piles.get(i).getState(),piles.get(i).getChargingId(),piles.get(i).getWaitId(),piles.get(i).getChargingNumber(),
                        piles.get(i).getChargingTime(),piles.get(i).getChargingAmount(),piles.get(i).getChargingCost(),piles.get(i).getServiceCost(),
                        piles.get(i).getTotalCost(),piles.get(i).getWaitAmount(),piles.get(i).getLeftTime());
            }
        }
    }

    public void start(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                calTimePri();
                calFee();
                getUsers();
                checkEnd();
            }
        }, 0 , 3000);
    }

    /*
    //查询充电状态
    //从详单中对应user_id查询最新一条
    @GetMapping("/chargingRecord/queryChargingState")
    public Map<String, Object> queryChargingState(){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        List<chargingRecord> chargingRecords = theadminMapper.getChargingRecord();
        if (chargingRecords.size()==0){
            result.put("code",20000);
            result.put("message","success");
            result.put("data","0");
            return result;
        }
        chargingRecord tmp = chargingRecords.get(chargingRecords.size()-1);
        result.put("code",20000);
        result.put("message","success");
        result.put("data",tmp.getChargingState());
        return result;
    }
    */
    //查询空闲充电桩有几个
    @GetMapping("/chargingPile/spare")
    public Map<String, Object> spare(){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        List<pile>  pile = theadminMapper.getFreePiles();
        result.put("code",20000);
        result.put("message","success");
        result.put("data",pile.size());
        return result;
    }


    //查询充电桩等待队列
    @GetMapping("/chargingPile/wait")
    public Map<String, Object> wait(@RequestParam("id") String id){

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        int pileid= Integer.parseInt(id);
        if(theadminMapper.getWaitid(pileid)== null||theadminMapper.getWaitid(pileid).equals("0"))
        {
            result.put("code",20003);
            result.put("message","没有等待的用户");
            result.put("data",null);
            return result;
        }
        else{
            int waitid = Integer.parseInt(theadminMapper.getWaitid(pileid));
            data.put("waitId",waitid);
            data.put("mode",theadminMapper.getType(pileid));
            data.put("amount",theadminMapper.getWaitamount(pileid));
            result.put("code",20000);
            result.put("message","success");
            result.put("data",data);
            return result;
        }


    }



}