package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member=new Member("userA");
        Member saveMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(saveMember.getId());
        assertEquals(saveMember.getId(),findMember.getId());
        assertEquals(saveMember.getUsername(),findMember.getUsername());
    }

    @Test
    void basicCRUD() {
        Member member1=new Member("user1");
        Member member2=new Member("user2");

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        //단건 조회
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        assertEquals(findMember1,member1);
        assertEquals(findMember2,member2);

        //리스트 조회
        List<Member> findAll = memberJpaRepository.findAll();
        assertEquals(findAll.size(),2);

        //카운트 검증
        long count = memberJpaRepository.count();
        assertEquals(count,2);

        //삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        long deleteCount = memberJpaRepository.count();
        assertEquals(deleteCount,0);
    }
    //jqpl 여러 조건 나오는지
    @Test
    public void findByUSernameAndAge(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 120);

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUSernameAndAge("AAA", 9);

        assertEquals(result.size(),1);
        assertEquals(result.get(0).getUsername(),"AAA");
        assertEquals(result.get(0).getAge(),10);
    }

    //페이징 jpa만 사용
    @Test
    public void paging(){
        memberJpaRepository.save(new Member("member1",10));
        memberJpaRepository.save(new Member("member2",10));
        memberJpaRepository.save(new Member("member3",10));
        memberJpaRepository.save(new Member("member4",10));
        memberJpaRepository.save(new Member("member5",10));

        int age=10;
        int offset=0;
        int limt=3;

        List<Member> members = memberJpaRepository.findByPage(age, offset, limt);
        long totalCount = memberJpaRepository.totalCount(age);

        assertEquals(members.size(),3);
        assertEquals(totalCount,5);
    }

}