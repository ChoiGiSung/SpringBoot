package jpabook.jpashop.api;

import jpabook.jpashop.Domain.Address;
import jpabook.jpashop.Domain.Order;
import jpabook.jpashop.Domain.OrderStatus;
import jpabook.jpashop.Repository.OrderRepository;
import jpabook.jpashop.Repository.OrderSearch;
import jpabook.jpashop.Repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
* xToOne (many,one)관계 문제해결
*     //api에서 orders안보이게 + 양방향 관계에서는 한쪽은 jsonIgnore를 해줘야 한다
* order 연관관계
* order -> member
* order -> delivery
* */
//주문 조회
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    // ==V1==//
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        //Lazy 강제 초기화
        for(Order order: all){
            order.getMember().getName(); //lazy강제 초기화
            order.getDelivery().getAddress(); //lazy강제 초기화
        }
     
        return all;
    }

    // ==V2==//
    //v1과 v2의 문제점은 둘다 쿼리가 너무 많이 호출한다!
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto>orderV2(){
        //원래 리스트 반환이 아닌 result로 반환하는게 맞다 memberApi에 잘 만들어 놨다
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        //for문이나 stream 써서 바꿔치기
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        //객체를 리스트로 반환
        return result;

    }

    @Data
    static class SimpleOrderDto{
        //api호출 시 보여줄 꺼
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address; //배송지 정보

        public SimpleOrderDto(Order order) {
            orderId=order.getId();
            name=order.getMember().getName();//여기서 lazy 초기화됨 여기서  값이 없으면 db에 ㅜ커리를 날린다
            orderDate=order.getOrderDate();
            orderStatus=order.getStatus();
            address=order.getDelivery().getAddress();//여기서 lazy 초기화됨 여기서  값이 없으면 db에 ㅜ커리를 날린다
        }
    }

    //==v3==//
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3(){
        List<Order> orders=orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
//SimpleOrderDto 이제 한방쿼리로 다 조인해서 가져와서 lazy초기화를 하지 않는다
        return result;
    }
    //==v4==//
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4(){
       return orderRepository.findOrderDtos();
    }

}
