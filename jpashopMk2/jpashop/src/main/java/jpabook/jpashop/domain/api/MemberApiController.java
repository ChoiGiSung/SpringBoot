package jpabook.jpashop.domain.api;

import jpabook.jpashop.domain.domain2.Member;
import jpabook.jpashop.domain.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberApiController {
    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    //회원 등록api
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    //dto로 묶어서
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody CreateMemberRequest request){
        Member member=new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    @Data
    static class CreateMemberRequest{
        //dto
        private String name;
    }


    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    //이름 업데이트
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody UpdateMemberRequest request
    ){
        memberService.update(id,request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(),findMember.getName());
    }

    @Data
    private class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    private class UpdateMemberResponse {
        private Long id;
        private String name;
    }
}
