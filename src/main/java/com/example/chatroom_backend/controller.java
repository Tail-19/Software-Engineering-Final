package com.example.chatroom_backend;

import com.example.chatroom_backend.mybatis.entity.user;
import com.example.chatroom_backend.mybatis.mapper.userMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("http://127.0.0.1:4523/m1/2120303-0-default")
public class controller {
    userMapper theUserMapper;
    String defaultURL= "/pictures";
    @PostMapping("/user/register")
    public boolean register(user theUser){
        if (theUser.getPassword().length()>=8&&theUser.getUserName().length()!=0){
            theUser.setPictureURL(defaultURL);
            theUserMapper.register(theUser);
            return true;
        }
        return false;
    }
    @PostMapping("/user/change_info")
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
}
