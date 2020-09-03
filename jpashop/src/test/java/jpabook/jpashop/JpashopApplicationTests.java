package jpabook.jpashop;

import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
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
class JpashopApplicationTests {
	
	//test에 resource를 만들어서 main의 yml을 복사해서 놓고
	//in memory 로 db를 돌린다
	//아무 설정이 없으면 메모리모드로 돌리기 떄문에 없어도 된다

	@Autowired
	MemberService memberService;

	@Autowired
	MemberRepository memberRepository;

	//@Rollback(false)
	@Test void 회원가입 () throws Exception{
		//given
		Member member=new Member();
		member.setName("kim");

		//when
		Long saveId= memberService.join(member);

		//then
		Assertions.assertEquals(member,memberRepository.find(saveId));

	}

	@Test
	public void 중복_회원_예외 () throws Exception{
		//given
		Member member1=new Member();
		member1.setName("kim");

		Member member2=new Member();
		member2.setName("kim");

		//when
		memberService.join(member1);
		try {
			memberService.join(member2); //예외가 발생해야 한다
		}catch (IllegalStateException e){
			return;
		}

		//then
		Assertions.fail("예외가 발생해야 합니다.");
	}

}
