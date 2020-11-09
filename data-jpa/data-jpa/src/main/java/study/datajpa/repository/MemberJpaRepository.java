package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Member save(Member member){
        entityManager.persist(member);
        return member;
    }

    public Member find(Long id){
        return entityManager.find(Member.class,id);
    }

    public void delete(Member member){
        entityManager.remove(member);
    }

    public List<Member>findAll(){
        return entityManager.createQuery("select m from Member m",Member.class).getResultList();
    }


    //옵셔널로 조회
    public Optional<Member> findById(Long id){
        Member member=entityManager.find(Member.class,id);
        return Optional.ofNullable(member); //널일 수도 아닐수도 있다
    }

    //카운트 쿼리
    public long count(){
        return entityManager.createQuery("select count(m) from Member m",Long.class).getSingleResult();
        //단건의 singleResult
    }
}
