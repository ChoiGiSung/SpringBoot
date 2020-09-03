//package jpabook.jpashop.Repository;
//
//import jpabook.jpashop.Domain.Member;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//class MemberRepositoryTest {
//
//    @Autowired MemberRepository memberRepository;
//
//    @Test
//    @Transactional()
//    @Rollback(false)
//    public void testMember() throws Exception{
//        //given
//        Member member=new Member();
//        member.setUsername("membaerA");
//
//        //when
//        Long savedId= memberRepository.save(member);
//            //반환값은 아이디이니까
//        Member findMember= memberRepository.find(savedId);
//            //반환된 id값으로 멤버 조회
//
//        //then
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//
//        Assertions.assertThat(findMember).isEqualTo(member);
//    }
//}