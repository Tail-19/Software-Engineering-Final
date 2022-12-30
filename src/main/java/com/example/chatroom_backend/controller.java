package com.example.chatroom_backend;

import com.example.chatroom_backend.mybatis.entity.user;
import com.example.chatroom_backend.mybatis.entity.friend_list;
import com.example.chatroom_backend.mybatis.mapper.userMapper;
import com.example.chatroom_backend.mybatis.mapper.listMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class controller {

    @Resource
    userMapper theUserMapper;
    @Resource
    listMapper thelist;
    String basePath = controller.class.getClassLoader().getResource("").getPath()+"/pictures";
    String defaultURL= basePath+"/defaultPicture";
    @PostMapping("/user/register")
    public Map<String, Object> register(@RequestBody Map<String,String> input){
        user theUser = new user();
        theUser.setPictureURL(defaultURL);
        theUser.setUserName(input.get("userName"));
        theUser.setPassword(input.get("password"));
        Map<String, Object> result = new HashMap<>();
        if ((input.get("password").length()>=8&&input.get("userName").length()!=0)&&theUserMapper.findByName(input.get("userName"))==null){
            int userid;
            theUserMapper.register(theUser);
            result.put("success",true);
            result.put("userid", theUser.getUserId());
        }
        else{
            result.put("success",false);
            result.put("userid", null);
        }
        return result;
    }
    @PutMapping("/user/change_info")
    public Map<String, Object> change_info(@RequestBody Map<String,String> input){
        Map<String, Object> result = new HashMap<>();
        String newUserName = input.get("NewUserName");
        String newPassword = input.get("NewPassword");
        String userId = input.get("UserId");
        //int userId = (input.get("UserId").charAt(0))-'0';
        System.out.println(userId);
        if ((newUserName.length()!=0&&newPassword.length()>=8)&&search(userId) != null){
            theUserMapper.change_info(newUserName,newPassword,userId);
            result.put("success",true);
        }else{
            result.put("success",false);
        }
        return result;
    }

    @RequestMapping(value = "/user/search/{id}", method = RequestMethod.GET)
    public user search(@PathVariable("id")String userId){
        if (theUserMapper.search(userId)!=null){
            return theUserMapper.search(userId);
        }
        return null;
    }

    @PutMapping("/user/uploadPicture")
    public Map<String, Object> uploadPicture(@RequestBody Map<String,Object> input){
        Map<String, Object> result = new HashMap<>();
        MultipartFile picture = (MultipartFile)(input.get("picture"));
        String userId = (String)input.get("userId");
        if (picture!=null){
            String filePath = basePath+"/"+picture.getOriginalFilename();
            File desFile = new File(filePath);
            if (!desFile.getParentFile().exists()){
                desFile.mkdirs();
            }
            try{
                picture.transferTo(desFile);
            }catch (IllegalStateException|IOException e){
                e.printStackTrace();
            }
            theUserMapper.uploadPicture(filePath, userId);
            result.put("success", true);
        }else{
            result.put("success", false);
        }
        return result;
    }

    @GetMapping("/user/login")
    public Map<String, Object> login(int userName, String password){
        Map<String, Object> result = new HashMap<>();
        if (theUserMapper.login(userName, password).size()!=0){
            result.put("success", true);
        }else{
            result.put("success", false);
        }
        return result;
    }

    @PostMapping("/message/send_message")
    public Map<String, Object> send_message(@RequestBody Map<String,String> input){
        Map<String, Object> result = new HashMap<>();
        String receiveUserId = input.get("receiveUserId");
        String message = input.get("message");
        String senderUserId = input.get("senderUserId");
        theUserMapper.send_message(receiveUserId, message, senderUserId, LocalTime.now());
        result.put("success", true);
        return result;
    }


    @RequestMapping(value = "/message/get_message/{senderId}/{receiverId}", method = RequestMethod.GET)
    public Map<String, Object> get_message(@PathVariable("senderId") String senderId, @PathVariable("receiverId") String receiverId){
        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> msgList = theUserMapper.get_message(senderId, receiverId);
        if (msgList!=null){
            result.put("success",true);
        }else{
            result.put("success",false);
        }
        List<Map<String, String>> returnMsg = new ArrayList<>();
        for(int i=0;i<msgList.size();i++){
            Map<String, String>tmp = new HashMap<>();
            tmp.put("senderId", msgList.get(i).get("SENDERUSERID"));
            tmp.put("receiverId", msgList.get(i).get("RECEIVEUSERID"));
            tmp.put("text", msgList.get(i).get("CONTENT"));
            returnMsg.add(tmp);
        }
        result.put("message",returnMsg);
        return result;
    }


    //好友列表相关
    @GetMapping("/friend_list/{id}")
    public Map<String, Object> get_friendlist(@PathVariable("id") String id)
    {
        Map<String, Object> result = new HashMap<>();
        friend_list[] sqlresult = thelist.get_listbyid(id);
        System.out.println(sqlresult);
        int i=0;
        for(i=0;i<sqlresult.length;i++)
        {
            System.out.println(sqlresult[i]);
        }
        if (sqlresult!=null)
        {
            List<Map<String, Object>> friendlist=new ArrayList<>();
            for(i=0;i<sqlresult.length;i++)
            {
                Map<String, Object> a=new HashMap<>();
                a.put("userid",sqlresult[i].getFriendID());
                a.put("username",sqlresult[i].getFriendName());
                friendlist.add(a);
            }

            result.put("success",true);
            result.put("friend_list",friendlist);
        }
        else
        {
            result.put("success",false);
            result.put("friend_list",new ArrayList<>());
        }
        return result;
    }

    @PostMapping ("/friend_list/add_friend")
    public Map<String, Object> add_friendlist(@RequestBody Map<String,String> input)
    {
        String add_id = input.get("add_id");
        String user_id = input.get("user_id");
        Map<String, Object> result = new HashMap<>();
        String name1=theUserMapper.search(add_id).getUserName();
        String name2=theUserMapper.search(user_id).getUserName();
        //String name1="a";
        //String name2="b";
        boolean success=false;
        if(thelist.get_listbytwoid(add_id,user_id)==null)
        {
            thelist.add_list(add_id,name1,user_id,name2);
            thelist.add_list(user_id,name2,add_id,name1);
            success=true;
        }
        result.put("success",success);
        return result;
    }

    @DeleteMapping("/friend_list/delete")
    public Map<String, Object> delete_friendlist(@RequestBody Map<String,String> input)
    {
        String delete_id = input.get("delete_id");
        String user_id = input.get("user_id");
        Map<String, Object> result = new HashMap<>();
        boolean success=false;
        if(thelist.get_listbytwoid(delete_id,user_id)!=null)
        {
            thelist.deleteById(delete_id,user_id);
            thelist.deleteById(user_id,delete_id);
            success=true;
        }
        result.put("success",success);
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
