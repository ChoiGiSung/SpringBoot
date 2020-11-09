//package jpabook.jpashop.domain.service;
//
//import jpabook.jpashop.domain.domain2.Address;
//import jpabook.jpashop.domain.domain2.Member;
//import jpabook.jpashop.domain.domain2.Order;
//import jpabook.jpashop.domain.domain2.OrderStatus;
//import jpabook.jpashop.domain.exception.NotEnoughStockException;
//import jpabook.jpashop.domain.item.Book;
//import jpabook.jpashop.domain.item.Item;
//import jpabook.jpashop.domain.repository.OrderRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@Transactional
//class OrderServiceTest {
//    @Autowired
//    EntityManager entityManager;
//    @Autowired
//    OrderService orderService;
//    @Autowired
//    OrderRepository orderRepository;
//
//    @Test
//    void 상품주문() {
//        //given
//        Member member = createMember();
//
//        Book book = createBook("시골", 10000, 10);
//
//        int orderCount=2;
//        //when
//        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
//
//        //then
//        Order getOrder = orderRepository.findOne(orderId);
//
//        Assertions.assertEquals(OrderStatus.ORDER,getOrder.getStatus());
//        Assertions.assertEquals(1,getOrder.getOrderItems().size());
//        Assertions.assertEquals(10000*orderCount,getOrder.getTotalPrice());
//        Assertions.assertEquals(8,book.getStocQuantity());
//
//    }
//    @Test
//    void 재고수량_초과() throws Exception {
//        //given
//        Member member=createMember();
//        Item item=createBook("시골",10000,10);
//
//        int ordercount = 11;
//
//        //when
//        try{
//            orderService.order(member.getId(), item.getId(), ordercount);
//        }catch (NotEnoughStockException e){
//            return;
//        }
//        //then
//        Assertions.fail("재고수량 부족 예외가 발생해야한다");
//    }
//
//    @Test
//    void 주문취소(){
//        //given
//        Member member=createMember();
//        Book book=createBook("시골",10000,10);
//
//        int orderCount=2;
//
//        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
//
//        //when
//        orderService.cancelOrder(orderId);
//
//        //then
//        Order getOrder = orderRepository.findOne(orderId);
//
//        Assertions.assertEquals(OrderStatus.CANCEL,getOrder.getStatus());
//        Assertions.assertEquals(10,book.getStocQuantity());
//    }
//
//
//    private Book createBook(String name, int price, int stocQuantity) {
//        Book book=new Book();
//        book.setName(name);
//        book.setPrice(price);
//        book.setStocQuantity(stocQuantity);
//        entityManager.persist(book);
//        return book;
//    }
//
//    private Member createMember() {
//        Member member=new Member();
//        member.setName("A");
//        member.setAddress(new Address("S","R","123"));
//        entityManager.persist(member);
//        return member;
//    }
//
//}