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

            member.getFavoriteFoods().add("치킨1");
            member.getFavoriteFoods().add("치킨2");
            member.getFavoriteFoods().add("치킨3");

            member.getAddressEntities().add(new AddressEntity("das1","ad","Asd"));
            member.getAddressEntities().add(new AddressEntity("das2","ad","Asd"));

            em.persist(member);

            em.flush();
            em.clear();
            Member findmember1 = em.find(Member.class, member.getId());

            //값 타입은 변경 X
            //findmember1.getAddress().se
            //변경 방법은 갈아끼우기
            findmember1.setAddress(new Address("sad","ads","ASd"));
            
            //치킨-> 한식 값 타입 컬렉션
            //컬렉션String에서 값 변경은 값을 지우고 값을 다시 넣어야 한다.
            findmember1.getFavoriteFoods().remove("치킨");
            findmember1.getFavoriteFoods().add("한식");

            //컬렉션 객체 에서 값을 변경할떄고 값을 지우고 값을 다시 넣어줘야하는데
            //객체에 이퀄스와 해쉬코드가 오버라이딩 되어 있어야한다.
            //왜냐하면 remove는 이퀄스로 값을 삭제하니까
            findmember1.getAddressEntities().remove(new AddressEntity("das1","ad","Asd"));
            findmember1.getAddressEntities().add(new AddressEntity("das3","ad","Asd"));
            //값 타입 컬렉션은 변경사항이 있으면 주인엔티티와 연관된 모든 데이터를 삭제하고 , 값 타입 컬렉션에 있는 현재 값을 모두 다시 ! 저장한다.
            //그래서 잘 쓰지않는다 값타입객체 컬렉션
            //대안 방법은 엔티티로 묶어 내느것
            
            
            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            emf.close();

        }
    }
}
