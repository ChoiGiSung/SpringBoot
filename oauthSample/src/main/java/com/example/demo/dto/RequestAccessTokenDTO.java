package com.example.demo.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
 public class RequestAccessTokenDTO{
    String clientId = "sample";
    String redirectUri = "http://localhost:8080/login/github";
    String code ;
    String clientSecret ="sample";

    public RequestAccessTokenDTO() {
    }

    public RequestAccessTokenDTO(String code) {
        this.code = code;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getCode() {
        return code;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
