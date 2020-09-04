package jpabook.jpashop.service;

import jpabook.jpashop.Domain.Address;
import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Domain.Order;
import jpabook.jpashop.Domain.OrderStatus;
import jpabook.jpashop.Domain.item.Book;
import jpabook.jpashop.Domain.item.Item;
import jpabook.jpashop.Repository.OrderRepository;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember();

        Item book = createBook("시골 JPA", 10000, 10);

        int orderCount=2;
        //when

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        //상품주문시 상태는 order
        Assertions.assertEquals(OrderStatus.ORDER,getOrder.getStatus());
        //주문한 상품 종류 수가 정확해야 한다
        Assertions.assertEquals(1,getOrder.getOrderItems().size());
        //주문 가격은 가격 * 수량이다
        Assertions.assertEquals(10000*orderCount,getOrder.getTotalPrice());
        //주문수량만큼 재고가 줄어야한다
        Assertions.assertEquals(8,book.getStockQuantity());

    }



    @Test()
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;
        try {
            orderService.order(member.getId(), item.getId(), orderCount);
        }catch (NotEnoughStockException e){

        }
        //when


        //then
        //Assertions.fail("재고 수량 부족 예외가 발생해야함");

    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA",10000,10);

        int orderCount =2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        //주문 취소 상태는 CanCel이다
        Assertions.assertEquals(OrderStatus.CANCEL,getOrder.getStatus());
        //주문이 취소괸 상품은 그만큼 재고가 증가해야 한다
        Assertions.assertEquals(10,item.getStockQuantity());

    }




    private Item createBook(String name, int price, int stockQuantity) {
        Item book=new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","213-321"));
        em.persist(member);
        return member;
    }

}