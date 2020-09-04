package jpabook.jpashop.Domain;

import jpabook.jpashop.Domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {
    //주문시 들어가는 아이템들

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    //==생성 메서드 == //
    public static OrderItem createOrderItem(Item item,int orderPrice,int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        //재고 까기
        item.removeStock(count);
        return orderItem;
    }


    //==비지니스 로직==//
    public void cancel() {
        //오더 아이템도 주문 취소 된것을 알아야한다?
        getItem().addStock(count); //주문 수량 만큼 올리기 재고 수량을 원상복귀
    }

        //가격 조회
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
