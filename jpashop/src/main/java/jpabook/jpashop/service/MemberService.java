package jpabook.jpashop.service;

import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //이거는 파이널이 붙은거만 생성자 생성
public class MemberService {

    //필드 인젝션
    //@Autowired
    //변경할 일이 없으므로 final로 
    private final MemberRepository memberRepository;
    
//    //세터 인젝션
//    @Autowired //단점은 누군가가 호출해서 바꿀 수 있다  그래서 생성자 인젝션
//    public void setMemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

//    //생성자 인젝션
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //회원 가입
    @Transactional //이 클래스에 기본은 true고 이거는 false로 설정
    public Long join(Member member){
        //중복회원 검사
        valudateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //중복회원 검사 이름으로
    private void valudateDuplicateMember(Member member) {
        //예외
        List<Member> findMembers= memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원 입니다");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //단건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
