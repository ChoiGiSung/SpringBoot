package jpabook.jpashop;

import jpabook.jpashop.domain.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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

            //값 타입은 ok 값타입 컬렉션은 좀..



            //jpql -> 아무래도 String 이다 보니까 동적 쿼리를 만들기가 어렵다.
            List<Member> resultList = em.createQuery(
                    "select  m from Member m where m.username like '%keim'",
                    Member.class).getResultList();

            //criteriaBuilder 이용 쿼리를 코드로 만든다 -> 동적쿼리를 맘대로 다룰 수 있다
            CriteriaBuilder cb=em.getCriteriaBuilder();
            CriteriaQuery<Member> query=cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);
            CriteriaQuery<Member> cq =query.select(m).where(cb.equal(m.get("username"),"kim"));

            List<Member> resultList1 = em.createQuery(cq).getResultList();


            //jqpl연습
            TypedQuery<Member> select_m_from_member_m = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> select_m_from_member_m2 = em.createQuery("select m.name from Member m",String.class); //주어지는 타입
            Query query1 = em.createQuery("select m.name,m.age from Member m");//주어지는 타입
            //여러개를 받을때(반환 타입이 명확하지 않으면) 쿼리를 사용

            //값이 하나만 있을때는 싱글리절트를 쓸 수 도 있다
            TypedQuery<Member> query2 = em.createQuery("select m from Member m where m.username =:usernname", Member.class);
            query2.setParameter("username","member1");
            Member singleResult = query2.getSingleResult();

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            emf.close();

        }
    }
}
