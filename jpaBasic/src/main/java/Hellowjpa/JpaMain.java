package Hellowjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){
        //팩토리는 하나만 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //트랜젝션하나당 매니저 생성해
        EntityManager em = emf.createEntityManager();
        //엔티티 매니저는 쓰레드간에 공유x
        //jpa의 모든 데이터 변경은 트랜젝션 안에서!

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //code부분
        try{
           // 등록시
            //Member member=new Member();
//            member.setId(1L);
//            member.setName("helloA");
            //em.persist(member);


//            //조회
            Member findMember = em.find(Member.class, 1L);
//            System.out.println(findMember.getId()+ findMember.getName());
            //삭제
        //em.remove(findMember);

            //수정
           // findMember.setName("helloJpa");

            //jpa는 테이블이 아닌 엔티티 객체를 대상으로 검색하는 것이 목적
            //jpql은 jpa로만 부족한 검색 조건을 만족하기 위해 탄생
            
            //jpql은 엔티티 객체를 대상으로 쿼리
            //sql은 데이터 베이스 테이블을 대상으로 쿼리
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(7)
                    .getResultList(); //페이징 4번부터 7번까지 가져와

//            for(Member member: result){
//                System.out.println(member.getName());
//            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
