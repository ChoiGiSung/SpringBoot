package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ORDERS")
public class Order {

    @Id
    @GeneratedValue
    @Column(name="ORDER_ID")
    private Long id;

//    @Column(name="MEMBER_ID") //db대로 설계했을 경우
//    private Long memberId;
    @JoinColumn(name ="MEMBER_ID") //단방향으로 설계
    @ManyToOne
    private Member member;

    //하이버네이트는 자동 매핑되서 @TEMTIONAL을 안 써도 된다
    private LocalDateTime orderDate;
    
    @Enumerated(EnumType.STRING) //이넘은 스트링으로 명시해줘라
    private OrderStatus status;

    @OneToMany(mappedBy = "order") //연관관계의 주인은 order야 //양방향이 아니라면 없어도 되는 것
    private List<OrderItem> orderItems=new ArrayList<>();
    //조회만을 위한 orderItems

    @OneToOne
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;



    public void addOrderItem(OrderItem orderItem) {
        //연관관계 편의 메소드
        //오더아이템리스트에 오더를 넣고 아이템에도 오더를 넣어준다
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }


}
