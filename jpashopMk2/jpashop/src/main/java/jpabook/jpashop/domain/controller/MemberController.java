package jpabook.jpashop.domain.controller;

import jpabook.jpashop.domain.domain2.Address;
import jpabook.jpashop.domain.domain2.Member;
import jpabook.jpashop.domain.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String creatForm(Model model){

        model.addAttribute("memberForm",new MemberForm());
        //빈 껍데기를 가지고 간다
        return "members/createMemberForm";

    }

    @PostMapping("/members/new")        //      오류를 잡는 result
    public String create(MemberForm memberForm, BindingResult result){
        //이제는 파라 미터로 넘어오게 된다

        //오류 검사
        if(result.hasErrors()){
            //오류가 있으면 오류메세지를 가지고 다시 돌아간다, 기본 정보도 가지고
            return "members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member=new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }


    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
