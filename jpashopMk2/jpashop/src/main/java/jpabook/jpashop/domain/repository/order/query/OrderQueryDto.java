package jpabook.jpashop.domain.repository.order.query;

import jpabook.jpashop.domain.domain2.Address;
import jpabook.jpashop.domain.domain2.OrderStatus;
import lombok.Data;
import java.util.*;

import java.time.LocalDateTime;

@Data
public class OrderQueryDto {
    private Long id;
    private String name;
    private LocalDateTime localDateTime;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long id, String name, LocalDateTime localDateTime, OrderStatus orderStatus, Address address, List<OrderItemQueryDto> orderItems) {
        this.id = id;
        this.name = name;
        this.localDateTime = localDateTime;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = orderItems;
    }
}
