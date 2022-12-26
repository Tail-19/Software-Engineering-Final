package com.example.chatroom_backend;

import com.example.chatroom_backend.mybatis.entity.user;
import com.example.chatroom_backend.mybatis.mapper.userMapper;
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
    String basePath = controller.class.getClassLoader().getResource("").getPath()+"/pictures";
    String defaultURL= basePath+"/defaultPicture";
    @PostMapping("/user/register")
    public boolean register(user theUser){
        if (theUser.getPassword().length()>=8&&theUser.getUserName().length()!=0){
            theUser.setPictureURL(defaultURL);
            theUserMapper.register(theUser.getUserName(),theUser.getPassword(),theUser.getPictureURL());
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
}
