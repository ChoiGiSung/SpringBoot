package jpabook.jpashop.domain;

import jpabook.jpashop.domain.domain2.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;



@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;

    @PostConstruct//빈 다 생성하고 마지막에
    public void init(){

        initService.dbInit1();;
        initService.dbInit2();;
    }



    @Component
    @Transactional
    @RequiredArgsConstructor
    static  class  InitService{
        private final EntityManager em;

        public void dbInit1(){
            Member member = createMember("A", "서울");
            em.persist(member);

            Book book1 = createBook("jpaA", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("jpaB", 20000, 200);
            em.persist(book2);


            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery=new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }



        public void dbInit2(){
            Member member = createMember("B", "진주");
            em.persist(member);

            Book book1 = createBook("jpaA", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("jpaB", 20000, 200);
            em.persist(book2);


            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery=new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
        private Member createMember(String a, String name) {
            Member member = new Member();
            member.setName(a);
            member.setAddress(new Address(name, "!", "111"));
            return member;
        }
        private Book createBook(String jpaA, int price, int stocQuantity) {
            Book book1 = new Book();
            book1.setName(jpaA);
            book1.setPrice(price);
            book1.setStocQuantity(stocQuantity);
            return book1;
        }


    }
}
