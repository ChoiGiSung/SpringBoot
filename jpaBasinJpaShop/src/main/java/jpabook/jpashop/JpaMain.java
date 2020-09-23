package jpabook.jpashop;

import jpabook.jpashop.domain.*;

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

//            Child child1=new Child();
//            Child child2=new Child();
//
//            Parent parent=new Parent(); //부모 중심 코드
//            parent.addChild(child1);
//            parent.addChild(child2);
//
//            em.persist(parent);
//            em.persist(child1); //자식을 퍼시스트 안해주면 에러발생
//            em.persist(child2);

            Member member=new Member();
            member.setName("dd");

            member.setAddress(new Address("das","ad","Asd"));

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
