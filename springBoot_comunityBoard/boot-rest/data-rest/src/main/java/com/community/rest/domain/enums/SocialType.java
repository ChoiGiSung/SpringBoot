package com.community.rest.domain.enums;

public enum SocialType {
    GOOGLE("google"),
    KAKAO("kakao");

    private final String ROLE_PREFIX="ROLE_";
    private String name;

    SocialType(String name) {
        this.name = name;
    }

    public String getRoleType(){
        return ROLE_PREFIX+name.toUpperCase();
        //반환 값으로 ROLE_google,ROLE_kakao를 준다
    }
    public String getValue(){
        return name;
    }

    public boolean isEquals(String authority){
        return this.getRoleType().equals(authority);
    }
}
