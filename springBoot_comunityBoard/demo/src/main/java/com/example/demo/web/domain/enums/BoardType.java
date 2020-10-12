package com.example.demo.web.domain.enums;

import javax.persistence.Embeddable;

public enum BoardType {

    notice("공지사항"),
    free("자유게시판");

    private String value;


    BoardType(String value){
        this.value=value;
    }
    public String getValue(){
        return this.value;
    }

}
