package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.domain2.Member;
import jpabook.jpashop.domain.repository.MemberRpository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    private final MemberRpository memberRpository;

    public MemberService(MemberRpository memberRpository) {
        this.memberRpository = memberRpository;
    }

    //회원 가입
    public Long join(Member member){
        validateDuplicateMember(member); //중복회원 검사
        memberRpository.save(member);
        return member.getId();
    }

    //중복회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRpository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 있는 회원");
        }
        //멀티 스레드 상황을 대비해 db에 유니크 제약조건을 거는게 좋다
    }

    //회원 전체 조회
    @Transactional(readOnly = true)//->성능 최적화 조회시 에만
    public List<Member> findMembers(){
        return memberRpository.findALlMember();
    }

    //한건 조회
    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRpository.findMember(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        //변경감지로 업데이트
        Member member = memberRpository.findMember(id);
        member.setName(name);
    }
}
