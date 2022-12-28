package com.example.chatroom_backend;

import com.example.chatroom_backend.mybatis.entity.user;
import com.example.chatroom_backend.mybatis.entity.friend_list;
import com.example.chatroom_backend.mybatis.mapper.userMapper;
import com.example.chatroom_backend.mybatis.mapper.listMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("http://127.0.0.1:4523/m1/2120303-0-default")
public class controller {
    userMapper theUserMapper;
    listMapper thelist;
    String basePath = controller.class.getClassLoader().getResource("").getPath()+"/pictures";
    String defaultURL= basePath+"/defaultPicture";
    @PostMapping("/user/register")
    public boolean register(user theUser){
        if (theUser.getPassword().length()>=8&&theUser.getUserName().length()!=0){
            theUser.setPictureURL(defaultURL);
            theUserMapper.register(theUser.getUserName(),theUser.getPassword(),theUser.getPictureURL());
            friend_list newlist=new friend_list();//新建用户时同时新建对应的用户列表，这样两个数据库同一用户的id相同
            newlist.setOwner(theUser.getUserName());
            user[] user_list={};
            newlist.setItems(user_list);
            thelist.add_newlsit(newlist);
            return true;
        }
        return false;
    }
    @PutMapping("/user/change_info")
    public boolean change_info(String newUserName, String newPassword, HttpServletRequest request){
        String userId = null;
        Cookie[] cookies=request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("userId")){
                    userId=cookie.getValue();
                }
            }
        }
        if ((newUserName.length()!=0&&newPassword.length()>=8)&&userId != null){
            theUserMapper.change_info(newUserName,newPassword,userId);
            return true;
        }
        return false;
    }

    @GetMapping("/user/search")
    public user search(String id){
        return theUserMapper.search(id);
    }

    @PutMapping("/user/uploadPicture")
    public boolean uploadPicture(MultipartFile picture){
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
            return true;
        }
        return false;
    }

    @GetMapping("/user/login")
    public boolean login(String userName, String password){
        if (theUserMapper.login(userName, password)!=null){
            return true;
        }
        return false;
    }

    @PostMapping("/message/send_message")
    public boolean send_message(String userId, String message){
        theUserMapper.send_message(userId,message, LocalTime.now());
        return true;
    }

    @GetMapping("/message/get_message")
    public Map<String, Object> get_message(String userId){
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
        //String ownername=theUserMapper.search(id).getUserName();
        friend_list sqlresult = thelist.get_listbyid(Integer.parseInt(id));
        if (sqlresult!=null)
        {
            result.put("success",true);
            result.put("friend",sqlresult.getItems());
        }
        else
        {
            result.put("success",false);
            Object[] friend=new Object[1];
            result.put("friend",friend);
        }
        return result;
    }

    @PostMapping ("/friend_list/add_friend")
    public Map<String, Object> add_friendlist(@RequestParam("add_id") String add_id,@RequestParam("user_id") String user_id)
    {

        Map<String, Object> result = new HashMap<>();
        friend_list sql_user = thelist.get_listbyid(Integer.parseInt(user_id));
        friend_list sql_add = thelist.get_listbyid(Integer.parseInt(add_id));
        boolean success=false;
        if(sql_add!=null&&sql_user!=null)
        {
            user[] new_user=new user[sql_user.getItems().length+1];
            int i=0;
            for(i=0;i<sql_user.getItems().length;i++)
            {
                new_user[i]=sql_user.getItems()[i];
            }
            new_user[sql_user.getItems().length]=theUserMapper.search(add_id);
            sql_user.setItems(new_user);
            thelist.update_list(sql_user);
            user[] new_add=new user[sql_add.getItems().length+1];
            for(i=0;i<sql_add.getItems().length;i++)
            {
                new_add[i]=sql_add.getItems()[i];
            }
            new_add[sql_add.getItems().length]=theUserMapper.search(add_id);
            sql_add.setItems(new_add);
            thelist.update_list(sql_add);
            success=true;
        }
        result.put("success",success);
        return result;
    }

    @DeleteMapping("/friend_list/delete")
    public Map<String, Object> delete_friendlist(@RequestParam("delete_id") String delete_id,@RequestParam("user_id") String user_id)
    {
        Map<String, Object> result = new HashMap<>();
        friend_list sql_user = thelist.get_listbyid(Integer.parseInt(user_id));
        friend_list sql_delete = thelist.get_listbyid(Integer.parseInt(delete_id));
        Boolean success=false;
        if(sql_delete.exist(user_id)&&sql_user.exist(delete_id))
        {
            user[] new_user=new user[sql_user.getItems().length-1];
            int i=0,j=0;
            for(i=0;i<sql_user.getItems().length;i++)
            {
                if(!sql_user.getItems()[i].equals(delete_id))
                {
                    new_user[j]=sql_user.getItems()[i];
                    j++;
                }
            }
            sql_user.setItems(new_user);
            thelist.update_list(sql_user);
            user[] new_delete=new user[sql_delete.getItems().length-1];
            j=0;
            for(i=0;i<sql_delete.getItems().length;i++)
            {
                if(!sql_delete.getItems()[i].equals(user_id))
                {
                    new_delete[j]=sql_delete.getItems()[i];
                    j++;
                }
            }
            sql_delete.setItems(new_delete);
            thelist.update_list(sql_delete);
            success=true;
        }
        result.put("success",success);
        return result;
    }

}
