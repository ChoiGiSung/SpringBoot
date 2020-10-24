package jpabook.jpashop.domain.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    //벨리데이션이 있는데 어노테이션이 안먹혀서 더 찾아보자
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
