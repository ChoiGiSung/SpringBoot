package jpabook.jpashop.Domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    //다대일 관계 //양방향이다
    @ManyToOne(fetch = FetchType.LAZY)
    //LAZY로 지연로딩이 되니 new member를 주는게 아닌 (지연로딩 = db에서 안끌고 온다 order의 데이터만 가지고 온다[내가 지금 오더를 api로 내보내려하니까])
    //그래서 mamber를 주는게 아닌 proxy멤버를 주는거다
    //근데 json이 order로 member를 사용해야하는데 proxy멤버이니 오류가 발생한거다
    @JoinColumn(name="member_id") //외래키가 된다 연관관계의 주인
    private Member member;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems=new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [order,cancle] //enum으로 생성

    //===연관관계 메서드=//
    public void setMember(Member member){
        this.member=member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }


    //==생성 메서드==//
    
    //주문의 완성 첨 만들때부터 완성시켜버려
    public static Order createOrder(Member member,Delivery delivery,OrderItem... orderItems){
        Order order = new Order();
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
    
    //주문취소
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            //이미 배송 완료면 취소 x
            throw new IllegalStateException("이미 배송된 상품은 취소가 불가능");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem: orderItems){
            orderItem.cancel();
        }
    }
    //==조회로직==//

    //전체 주문 가격 조회
    public int getTotalPrice(){
        int totalPrice=0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
        //스트림이나 람다로 깔끔하게 할 수 있다
        //      return = rderItems.stream().
        //      mapToInt(OrderItem::getTotalPrice)
        //      .sum();
    }
}
