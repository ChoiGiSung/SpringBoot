package jpabook.jpashop.domain.api;

import jpabook.jpashop.domain.domain2.Order;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.domain.repository.SimpleOrderQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;



    //주문 조회 페치 조인 사용
    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderQueryDto> ordersV4(){
        List<Order> orders = orderRepository.findOrderDtos();
        List<SimpleOrderQueryDto> result=orders.stream()
                .map(o->new SimpleOrderQueryDto(o))
                .collect(Collectors.toList());
        return result;
    }



}
