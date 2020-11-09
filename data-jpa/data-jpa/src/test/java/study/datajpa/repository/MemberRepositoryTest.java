package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

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
}