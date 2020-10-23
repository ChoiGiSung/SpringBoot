package jpabook.jpashop.domain.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class MemberRpository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Member member){
        entityManager.persist(member);
    }

    public Member findMember(Long id){
        return entityManager.find(Member.class,id);
    }

    public List<Member> findALlMember(){
        List<Member> result = entityManager.createQuery("select m from Member m", Member.class).getResultList();
        return result;
    }

    public List<Member>findByName(String name){
        return entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
