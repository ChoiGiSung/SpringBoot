package jpabook.jpashop.Repository;

import jpabook.jpashop.Domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;
    //엔티티 매니저를 알아서 주입해준다

    public Long save(Member member){
        //저장하는 코드
        em.persist(member);
        return member.getId();
        //커맨드와 쿼리를 불리해라 
        //리턴값을 안남기는게 좋지만 혹시모를 나중을 위해 아이디를 리턴한다
    }

    public Member find(Long id){
        return em.find(Member.class,id);
    }

}
