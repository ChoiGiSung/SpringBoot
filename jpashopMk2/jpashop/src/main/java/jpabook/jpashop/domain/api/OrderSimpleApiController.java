package jpabook.jpashop.domain.api;

import jpabook.jpashop.domain.domain2.Order;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.domain.repository.SimpleOrderQueryDto;
import jpabook.jpashop.domain.repository.order.query.OrderQueryDto;
import jpabook.jpashop.domain.repository.order.query.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;




    //주문 조회 페치 조인 사용
    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderQueryDto> ordersV4(){
        List<Order> orders = orderRepository.findOrderDtos();
        List<SimpleOrderQueryDto> result=orders.stream()
                .map(o->new SimpleOrderQueryDto(o))
                .collect(Collectors.toList());
        return result;
    }


    //to one 관계 페이징
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto>orderV3_page(@RequestParam(value = "offset",defaultValue = "0")int offset,
                                      @RequestParam(value = "limit",defaultValue = "100")int limit){
        List<Order>orders=orderRepository.findOrderDtos(offset,limit);

        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

        return collect;

    }

    private class OrderDto {
        private Long orderId;
        private String name;

        public OrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
        }
    }

    //jpa에서 dto 직접 조회
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersQueryV4(){
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersQueryV5(){
        return orderQueryRepository.findAllByDto_opti();
    }


}
