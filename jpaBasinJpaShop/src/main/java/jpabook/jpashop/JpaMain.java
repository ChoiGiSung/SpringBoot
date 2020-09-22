package jpabook.jpashop;

import jpabook.jpashop.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args){
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello"); //persistance.xml에서 정의한 이름
        EntityManager em=emf.createEntityManager();
        EntityTransaction tx= em.getTransaction();

        tx.begin();

        try{
//            Order order=new Order();
//            order.addOrderItem(new OrderItem());

            Book book=new Book();
            book.setName("JPA");
            book.setAuther("das");

            em.persist(book);
            em.flush();
            em.clear();

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            emf.close();

        }
    }
}
