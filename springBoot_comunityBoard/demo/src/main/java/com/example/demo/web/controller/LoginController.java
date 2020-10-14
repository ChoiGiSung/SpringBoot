package com.example.demo.web.controller;

import com.example.demo.web.annotation.SocialUser;
import com.example.demo.web.domain.User;
import com.example.demo.web.domain.enums.SocialType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/loginSuccess")
    public String loginComplete(HttpSession session
                                ,@SocialUser User user){
        //aop를 사용하기 전의 불필요한 로직
//        OAuth2Authentication authentication= (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
//        Map<String,String>map= (HashMap<String, String>) authentication.getUserAuthentication().getDetails();
//
//        session.setAttribute("user", User.builder()
//        .name(map.get("name"))
//        .email(map.get("email"))
//        .pincipal(map.get("id"))
//        .socialType(SocialType.FACEBOOK)
//        .createDate(LocalDateTime.now())
//        .build());

        return "redirect:/board/list";
    }
}
