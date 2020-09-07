package jpabook.jpashop;

import jpabook.jpashop.Domain.*;
import jpabook.jpashop.Domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

//총 주문 2개
//jpa user1이 주문한번에 jpaBook1와 jpaBook2 두개를 산다
//jpa user2가 주문 한번에 spring book1과 2 두개를 산다

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        //어플로딩시점에 호출해 주려고
        initService.deInit1();
        initService.deInit2();
    }
    //라이프 사이클이 있어서 service의 소스코드를 다 postConsruct에 넣긴 힘들다

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;

        public void deInit1(){
            Member member = createMember("userA", "서울", "1", "11");

            Book book1 = createBook("JPA1 BOOK", 10000, 100);

            Book book2 = createBook("JPA2 BOOK", 20000, 100);

            //주문
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);


            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }



        public void deInit2(){
            Member member = createMember("userB", "부산", "2", "22");

            Book book1 = createBook("spring1 BOOK", 20000, 200);

            Book book2 = createBook("spring2 BOOK", 30000, 300);

            //주문
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);


            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }


        @NotNull
        private Member createMember(String name, String city, String street,
                                    String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            em.persist(member);
            return member;
        }
        @NotNull
        private Book createBook(String s, int i, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(s);
            book1.setPrice(i);
            book1.setStockQuantity(stockQuantity);
            em.persist(book1);
            return book1;
        }

        @NotNull
        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }


    }
}

