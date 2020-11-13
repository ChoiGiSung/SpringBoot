package study.datajpa.repository.CustomRepository;

import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

//이름은 spring data jpa의 repository와 맞춰줘야 한다 **
public class MemberRepositoryImpl implements MemberRepositoryCustom{
    //spring data jpa가 아닌
    //내가 직접 만들고 싶을때

    
    //memberRepository에서 실해을 하면 여기꼐 실행된다
    //java에서 되는게 아니고 spring data jpa에서 엮어 주는 거다.
    private final EntityManager entityManager;

    public MemberRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Member> findMemberCustom() {
        return entityManager.createQuery("select m from Member m")
                .getResultList();
         
    }

}
