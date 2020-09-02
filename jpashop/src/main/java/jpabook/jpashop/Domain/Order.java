package jpabook.jpashop.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    //다대일 관계 //양방향이다
    @ManyToOne
    @JoinColumn(name="member_id") //외래키가 된다 연관관계의 주인
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems=new ArrayList<>();

    @OneToOne
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [order,cancle] //enum으로 생성

}
