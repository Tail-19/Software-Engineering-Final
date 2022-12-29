package com.example.chatroom_backend;

import com.example.chatroom_backend.mybatis.entity.user;
import com.example.chatroom_backend.mybatis.entity.friend_list;
import com.example.chatroom_backend.mybatis.mapper.userMapper;
import com.example.chatroom_backend.mybatis.mapper.listMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    public Map<String, Object> register(String userName, String password){
        user theUser = new user();
        theUser.setPictureURL(defaultURL);
        theUser.setUserName(userName);
        theUser.setPassword(password);
        Map<String, Object> result = new HashMap<>();
        if (password.length()>=8&&userName.length()!=0){
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
    public boolean change_info(String newUserName, String newPassword, String userId){
        if ((newUserName.length()!=0&&newPassword.length()>=8)&&search(userId) != null){
            theUserMapper.change_info(newUserName,newPassword,userId);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/user/search/{id}", method = RequestMethod.GET)
    public user search(@PathVariable("id")String userId){
        if (theUserMapper.search(userId)!=null){
            return theUserMapper.search(userId);
        }
        return null;
    }

    @PutMapping("/user/uploadPicture")
    public boolean uploadPicture(MultipartFile picture, String userId){
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
            return true;
        }
        return false;
    }

    @GetMapping("/user/login")
    public boolean login(String userName, String password){
        if (theUserMapper.login(userName, password).size()!=0){
            return true;
        }
        return false;
    }

    @PostMapping("/message/send_message")
    public boolean send_message(String userId, String message){
        theUserMapper.send_message(userId,message, LocalTime.now());
        return true;
    }


    @RequestMapping(value = "/message/get_message/{id}", method = RequestMethod.GET)
    public Map<String, Object> get_message(@PathVariable("id") String userId){
        Map<String, Object> result = new HashMap<>();
        List<String> msgList = theUserMapper.get_message(userId);
        if (msgList!=null){
            result.put("success",true);
        }else{
            result.put("success",false);
        }
        result.put("message",msgList);
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
                a.put("id",sqlresult[i].getFriendID());
                a.put("username",sqlresult[i].getFriendName());
                friendlist.add(a);
            }

            result.put("success",true);
            result.put("friend",friendlist);
        }
        else
        {
            result.put("success",false);
            result.put("friend",new ArrayList<>());
        }
        return result;
    }

    @PostMapping ("/friend_list/add_friend")
    public Map<String, Object> add_friendlist(@RequestParam("add_id") String add_id,@RequestParam("user_id") String user_id)
    {

        Map<String, Object> result = new HashMap<>();
        String name1=theUserMapper.search(add_id).getUserName();
        String name2=theUserMapper.search(user_id).getUserName();
        //String name1="a";
        //String name2="b";
        boolean success=false;
        System.out.println(thelist.get_listbytwoid(add_id,user_id));
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
    public Map<String, Object> delete_friendlist(@RequestParam("delete_id") String delete_id,@RequestParam("user_id") String user_id)
    {
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

}
