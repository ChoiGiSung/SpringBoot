package jpabook.jpashop.Repository;

import jpabook.jpashop.Domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//빈으로 자동 등록
@Repository
@RequiredArgsConstructor //파이널 붙은거만 생성자 생성
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;
    //엔티티 매니저를 알아서 주입해준다

    public Long save(Member member){
        //저장하는 코드
        em.persist(member);
        return member.getId();
        //커맨드와 쿼리를 불리해라 
        //리턴값을 안남기는게 좋지만 혹시모를 나중을 위해 아이디를 리턴한다
    }

    //단건 조회
    public Member findOne(Long id){
        return em.find(Member.class,id);
    }

    //리스트 조회
    public List<Member> findAll(){
        List<Member> result= em.createQuery("select m from Member m",Member.class)
                .getResultList();

        return result;
    }

    //이름으로 조회
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }



}
