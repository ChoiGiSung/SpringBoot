package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;


    @Test
    public void testMember() {
        Member member=new Member("userA");
        Member saveMember = memberRepository.save(member);

        //옵셔널이 반환되는데 간단하게 get으로 뽑자
        //-> 나중에는 다르게 뽑아야 한다.
        Member findMember = memberRepository.findById(saveMember.getId()).get();
        Assertions.assertEquals(saveMember.getId(),findMember.getId());
        Assertions.assertEquals(saveMember.getUsername(),findMember.getUsername());
    }

    @Test
    void basicCRUD() {
        Member member1=new Member("user1");
        Member member2=new Member("user2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조회
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertEquals(findMember1,member1);
        assertEquals(findMember2,member2);

        //리스트 조회
        List<Member> findAll = memberRepository.findAll();
        assertEquals(findAll.size(),2);

        //카운트 검증
        long count = memberRepository.count();
        assertEquals(count,2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deleteCount = memberRepository.count();
        assertEquals(deleteCount,0);
    }

    //jqpl 여러 조건 나오는지 를 spring data jpa로
    @Test
    public void findByUSernameAndAge(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 120);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 9);

        assertEquals(result.size(),1);
        assertEquals(result.get(0).getUsername(),"AAA");
        assertEquals(result.get(0).getAge(),10);
    }

    //리포지토리 쿼리 정의
    @Test
    public void testQuery(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 120);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("AAA", 10);

        assertEquals(result.size(),1);
        assertEquals(result.get(0),m1);
    }

}