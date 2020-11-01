package jpabook.jpashop.domain.api;

import jpabook.jpashop.domain.domain2.Order;
import jpabook.jpashop.domain.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v3/orders")
    public List<OrderDto>orderV3(){
        List<Order> findOrder= orderRepository.findAllWithItem();
        List<OrderDto> collect = findOrder.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return collect;
    }

    @Data
    private class OrderDto {
        private Long id;
        private String uesername;

        public OrderDto(Order order) {
            this.id = order.getId();
            this.uesername = order.getMember().getName();
        }
    }


}
