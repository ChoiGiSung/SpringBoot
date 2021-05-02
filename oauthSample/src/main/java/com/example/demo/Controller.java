package com.example.demo;

import com.example.demo.dto.AccessToken;
import com.example.demo.dto.RequestAccessTokenDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@RestController
public class Controller {
    private String clientId = "sample";
    private String callBackUrl = "http://localhost:8080/login/github";
    private RestTemplate restTemplate = new RestTemplate();

    private String accessTokenUrl = "https://github.com/login/oauth/access_token";

    @GetMapping("/login")
    public void login(HttpServletResponse response){
        try {
            response.sendRedirect("https://github.com/login/oauth/authorize?client_id="+clientId+"&redirect_uri="+ callBackUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/login/github")
    public void callBack(@RequestParam("code")String code){
        //이제 받은 code로 access 토큰 요청
        AccessToken body = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, createHttpEntity(code), AccessToken.class).getBody();
        System.out.println(body.getAccessToken());
    }

    public HttpEntity<RequestAccessTokenDTO> createHttpEntity(String code){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<RequestAccessTokenDTO> entity =new HttpEntity<>(new RequestAccessTokenDTO(code),httpHeaders);
        return entity;
    }


}
