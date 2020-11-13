package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    //컨버터 사용

    private final MemberRepository memberRepository;


    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    //컨버터 사용 spring data jpa 가 알아서 만들어서 해준다.
//    @GetMapping("/members/{id}")
//    public String findMember2(@PathVariable("id") Member member){
//        return member.getUsername();
//    }


    //페이징
    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size=5)  Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
       //페이지는 엔티티를 담고 있으므로 dto로 변환해서 내보내기
        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        return map;
    }



    @PostConstruct
    public void init(){
        for(int i=0;i<100;i++){
            memberRepository.save(new Member("userA"+i));
        }
    }
}
