package jpabook.jpashop.domain.domain2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status; //주문 상태

    //=연관관계 편의 메소드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
        //멤버와 주문
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
        //오더와 오더아이템
    }

    public void setDelivery(Delivery delivery) {
        //this.setDelivery(delivery); 오류 무한참조
        this.delivery=delivery;
        delivery.setOrder(this);
    }

    protected Order() {
        //생성메서드 스타일을 쓰거 있으니까
        //생성자를 통해서 만들지마!
        //좋은 설계 좋은 유지보수!
    }

    //==생성메서드==//
    //필요 이유는 안에 들어있는거를 보면 상당히 복잡하다 그래서 메소드 호출을 통해 만들자
    public static Order createOrder(Member member,Delivery delivery,OrderItem... orderItems){
        Order order=new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem:orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비지니스 로직==//

    /*
    * 주문 취소소
    */
    public void cancel(){
        //이미 배송 완료이면
        if(delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송 완료");
        }
        //배송중이면
        this.setStatus(OrderStatus.CANCEL);

        for(OrderItem orderItem:this.orderItems){
            orderItem.cancel(); //오더 아이템에도 캔슬을 해줘야 한다. 연관관계 매소드와 같은 이유
        }
    }

    //==조회로직==//
    /*
    * 전체 주문 가격 조회
    * */
    public int getTotalPrice(){
        int totalPrice=0;
        for(OrderItem orderItem: orderItems){
            totalPrice+=orderItem.getToralPrice(); //-> 오더아이템에 주문 수량과 가격이 있어서 거기서 가져온다.
        }
        return totalPrice;
    }

}


