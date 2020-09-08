package jpabook.jpashop.api;

import jpabook.jpashop.Domain.Address;
import jpabook.jpashop.Domain.Order;
import jpabook.jpashop.Domain.OrderItem;
import jpabook.jpashop.Domain.OrderStatus;
import jpabook.jpashop.Repository.OrderRepository;
import jpabook.jpashop.Repository.OrderSearch;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    // == v1 ==// 엔티티 직접 노출
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItem.getItem().getName();
            } //laye 초기화 작업
        }
        return all;
    }
    // == v2 ==//  dto로 반환
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o)) //dto로 변환 1단계
                .collect(Collectors.toList()); 
        return collect;
    }

    @Data //or @Getter 
    static class OrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        
        private List<OrderItemDto>orderItems; //dto안에 엔티티가 있다 안좋다 엔티티에 대한 의존을 완전히 끊어야 한다
        // 그래서 얘도 클래스로 뺴서 dto로 변환 해야한다

        public OrderDto(Order order) {
            orderId=order.getId();
            name=order.getMember().getName();
            orderDate=order.getOrderDate();
            orderStatus=order.getStatus();
            address=order.getDelivery().getAddress();

            //한번더 dto 속으로 들어가기 dto로 변환 2단계 -> 엔티티를 안예 안보여주기
            orderItems=order.getOrderItems().stream()
                    .map(o -> new OrderItemDto(o))
                    .collect(Collectors.toList());

//            order.getOrderItems().stream().forEach(o->o.getItem().getName()); //프록시 초기화 이거 없으면 orderItems가 안보였다
//            orderItems=order.getOrderItems(); 따로 dto륾 안만들었을떄
        }
    }

    @Getter
    static class OrderItemDto{

        private String itemName; //상품 명
        private int orderPrice; //주문 가격
        private int count;  //주문 수향

        public OrderItemDto(OrderItem orderItem) {
            itemName=orderItem.getItem().getName();
            orderPrice=orderItem.getOrderPrice();
            count=orderItem.getCount();
        }
    }

    // == v3 ==//  dto로 반환
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
       List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

        return result;
    }


}
