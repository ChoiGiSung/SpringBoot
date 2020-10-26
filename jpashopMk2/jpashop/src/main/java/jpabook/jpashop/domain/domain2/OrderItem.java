package jpabook.jpashop.domain.domain2;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    protected OrderItem() {
        //생성메서드 스타일을 쓰거 있으니까
        //생성자를 통해서 만들지마!
    }

    //==생성 메서도==//
    public static OrderItem createOrderItem(Item item,int orderPrice,int count){
        OrderItem orderItem=new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        //주문수 만큼 재고를 삭제
        item.removeStock(count);
        return orderItem;
    }

    //==비지니스 로직==//
    public void cancel() {
        //아이템의 취소에는 재고를 늘려주는 기능이 있어야한다.
        getItem().addStock(count);
    }

    //==조회 로직==//
    public int getToralPrice() { //총 주문가격
        return getOrderPrice()*getCount();
    }
}
