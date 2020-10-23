package jpabook.jpashop.domain.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.PersistenceContext;

@Getter @Setter
public class OrderSearch {
    //검색어
    private String memberName;
    private OrderStatus orderStatus; //주문 상태
}
