package jpabook.jpashop.api;

import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

//@Controller @ResponseBody

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    
    //리스폰스는 다시 요청시 돌려주는 것

    //==V1==//
    @PostMapping("/api/v1/members")         //json을 알아서 member로 변환
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
        
        //지금은 null로 해도 들어간다
    }
    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    //==V2==//
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member=new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest{
        //별도의 dto를 만들어라
        //엔티티와 에이피아이 스펙을 분리하는게 가능해진다
        //절대로 엔티티를 외부로 노출하거나 파라미터로 줘서는 안된다
        @NotEmpty
        private String name;
    }
    
    // 1번이 주는 유일한 장점은 클래스를 안만들어도 된다.
    // 2번의 장점은 Entity가 변경되도 api 스펙이 바뀌지 않는다
    //member에 뭐가 들어가는지 알 수 있다



    //==수정 api
    @PutMapping("/api/v2/members/{id}") //응답 dto
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, //업데이트용 dto
                                               @RequestBody @Valid UpdateMemberRequest request){
        memberService.upadte(id,request.getName());
        Member findMember = memberService.findOne(id);
        //그냥 서비스의 update에서 리턴값으로 영속성이아닌 member를 리턴
        //해도 되지만 커맨드와 쿼리를 분리하기 위해서 여기서 한번더 조회해서 
        //사용한다 , 뭐 하나 조회 쿼리는 트래픽이 별로 발생하지 않는다고한다
        return new UpdateMemberResponse(findMember.getId(),findMember.getName());
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    //==조회 api

    //==V1==
    @GetMapping("/api/v1/members")
    public List<Member> memberV1(){
        return memberService.findMembers();
        //문제점 엔티티를 노출하고(가장 안됨)
        //회원정보만 보고 싶은데 orders도 보여준다
    }

    //==V2==
    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        //리스트 멤버를 멤버디티오로 변환

        return new Result(collect,collect.size());
        //오브젝트 타입으로 반환
        //배열로 내보내면 확장성이 용이하지 않다

    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        //오브젝트 타입으로 반환하기 위해 사용
        //확장성 용이 바로 밑의 count 처럼
        private T data;

        //카운트도 바로 넣을 수 있다
        private int count;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        //엔티티 노출하기 싫어서 dto로 변환하기 위한 dto

    //내가 노출할꺼만 노출한다
        //ex)이름
        private String name;
    }


}
