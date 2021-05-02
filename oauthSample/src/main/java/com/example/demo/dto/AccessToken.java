package com.example.demo.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public
class AccessToken {
    String accessToken;

    public AccessToken() {
    }

    public String getAccessToken() {
        return accessToken;
    }

}