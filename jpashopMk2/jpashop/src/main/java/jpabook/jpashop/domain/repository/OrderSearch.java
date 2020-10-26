package jpabook.jpashop.domain.repository;

import jpabook.jpashop.domain.domain2.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    //검색어
    private String memberName;
    private OrderStatus orderStatus; //주문 상태
}
