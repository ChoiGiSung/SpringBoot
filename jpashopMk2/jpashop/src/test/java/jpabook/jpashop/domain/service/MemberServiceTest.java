package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.domain2.Member;
import jpabook.jpashop.domain.repository.MemberRpository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberRpository memberRpository;
    @Autowired
    private MemberService memberService;

    @Test
    void join() {
        //given
        Member member=new Member();
        member.setName("A");

        //when
        Long saveId = memberService.join(member);

        //then
        Assertions.assertEquals(member,memberRpository.findMember(saveId));
    }

    @Test
    void 중복회원_검증() throws Exception{

        //given
        Member member1=new Member();
        member1.setName("A");
        Member member2=new Member();
        member2.setName("A");
        //when
        memberService.join(member1);
        try {
            memberService.join(member2);
        }catch (IllegalStateException e){
            return;
        }

        //then
        Assertions.fail("예와가 발생래야 한다.");
    }


}