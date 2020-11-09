package jpabook.jpashop.domain.service;


import jpabook.jpashop.domain.domain2.Delivery;
import jpabook.jpashop.domain.domain2.Member;
import jpabook.jpashop.domain.domain2.Order;
import jpabook.jpashop.domain.domain2.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.ItemRepository;
import jpabook.jpashop.domain.repository.MemberRepositoryOld;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.domain.repository.OrderSearch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepositoryOld memberRpository;
    private final ItemRepository itemRepository;

    public OrderService(OrderRepository orderRepository, MemberRepositoryOld memberRpository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.memberRpository = memberRpository;
        this.itemRepository = itemRepository;
    }
    //엔티티에 핵심로직이 있다 -> 도메안 모델 패턴
    //ex)cancel, createOrder 등
    //서비스는 위임만 하고 있다.

    //주문
    public Long order(Long memberId,Long itemId,int count){
        //주문자와 주문할 아이템, 그리고 개수를 받는다
        //그리고 여기서 주문을 다 조립해서 save시킨다
        //핵심로직은 entity 에서 구현이 되엇따.

        //엔티티 조회
        Member member= memberRpository.findMember(memberId);
        Item item= itemRepository.findOne(itemId);

        //배송 정보 생성
        Delivery delivery=new Delivery();
        delivery.setAddress(member.getAddress());

        //주문 상품 생성 장바구니 생성성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        //-> 원래는 딜리버리랑 오더 아디템을 persist 해준다음 order를 persist
        //해줘야하지만 cascade All해줘 서 다 알아서 persist된거다 연쇄persist
        
        return order.getId(); //주문번호 반납
    }


    //취소
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
        //-> order의 캔슬로 가면 상태를 바꿔주고
        // orderitem의 cancel을 호출해서 거기서 재고를 바꿔준다
        // 커밋될떄 다 더티체킹으로 걸려져서 update문이 나간다.

    }

    //검색
    public List<Order>findOrder(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }
}
