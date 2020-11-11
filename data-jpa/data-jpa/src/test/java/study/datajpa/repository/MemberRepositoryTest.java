package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;


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

    @Test
    void basicCRUD() {
        Member member1=new Member("user1");
        Member member2=new Member("user2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조회
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertEquals(findMember1,member1);
        assertEquals(findMember2,member2);

        //리스트 조회
        List<Member> findAll = memberRepository.findAll();
        assertEquals(findAll.size(),2);

        //카운트 검증
        long count = memberRepository.count();
        assertEquals(count,2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deleteCount = memberRepository.count();
        assertEquals(deleteCount,0);
    }

    //jqpl 여러 조건 나오는지 를 spring data jpa로
    @Test
    public void findByUSernameAndAge(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 120);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 9);

        assertEquals(result.size(),1);
        assertEquals(result.get(0).getUsername(),"AAA");
        assertEquals(result.get(0).getAge(),10);
    }

    //리포지토리 쿼리 정의
    @Test
    public void testQuery(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 120);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("AAA", 10);

        assertEquals(result.size(),1);
        assertEquals(result.get(0),m1);
    }
    //리포지토리 쿼리 정의 dto 읽어오기
    @Test
    public void findUsernameList(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 120);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> result = memberRepository.findUserName();
        for (String s : result) {
            System.out.println(s);
        }
    }

    // 여러개의 dto 조회 dto를 만들어서 가져와야 한다.
    @Test
    public void findMemberDto(){
        Member m1 = new Member("AAA", 10);


        Team team =new Team("teamA");
        teamRepository.save(team);
        m1.changeTeam(team);

        memberRepository.save(m1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto s : memberDto) {
            System.out.println("dto = "+s);
        }
    }

    //컬렉션 파라미터 바인딩 in절 사용
    @Test
    public void findByNames(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 120);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        for (Member s : result) {
            System.out.println("member = "+s);
        }
    }

    //페이징  사용
    @Test
    public void paging(){
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));

        int age=10;
        PageRequest pageRequset = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        //0~3 개  이름으로 sort해서 가져오기

        Page<Member> page = memberRepository.findByAge(age, pageRequset);
        //total 카운트까지 가져온다

        // api로 보여줄떄  dto로 변환 한 다음 보여줘야 한다.
        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), "team!"));
        //dto로 바꿨다.

        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        for (Member member : content) {
            System.out.println("member="+member);
        }
        System.out.println(totalElements);


        assertEquals(content.size(),3);
        assertEquals(page.getTotalElements(),5);//총갯수
        assertEquals(page.getNumber(),0);//현재 page번호
        assertEquals(page.getTotalPages(),2);//전체 페이지 수
        assertEquals(page.isFirst(),true);//처음?
        assertEquals(page.hasNext(),true);//다음 있음?


        //슬라이스 기능 더보기 기능
        //반환 타입만 바꿔서 사용할 수 있다.->1개를 더 읽어옴 -> 카운트 쿼리가 안나감
        Slice<Member> page2 = memberRepository.findSliceByAge(age, pageRequset);

        //주석은 지원 안함
        assertEquals(content.size(),3);
//        assertEquals(page.getTotalElements(),5);//총갯수
        assertEquals(page.getNumber(),0);//현재 page번호
//        assertEquals(page.getTotalPages(),2);//전체 페이지 수
        assertEquals(page.isFirst(),true);//처음?
        assertEquals(page.hasNext(),true);//다음 있음?
    }
}