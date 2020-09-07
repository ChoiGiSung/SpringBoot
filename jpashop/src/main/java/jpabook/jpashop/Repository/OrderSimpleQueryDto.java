package jpabook.jpashop.Repository;

import jpabook.jpashop.Domain.Address;
import jpabook.jpashop.Domain.Order;
import jpabook.jpashop.Domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;


// v4를 위한 dto
@Data
public class OrderSimpleQueryDto {
    //api호출 시 보여줄 꺼
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address; //배송지 정보

    public OrderSimpleQueryDto(Order order) {
        orderId=order.getId();
        name=order.getMember().getName();//여기서 lazy 초기화됨 여기서  값이 없으면 db에 ㅜ커리를 날린다
        orderDate=order.getOrderDate();
        orderStatus=order.getStatus();
        address=order.getDelivery().getAddress();//여기서 lazy 초기화됨 여기서  값이 없으면 db에 ㅜ커리를 날린다
    }
}