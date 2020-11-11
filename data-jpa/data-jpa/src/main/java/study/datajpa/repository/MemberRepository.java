package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import java.util.*;


public interface MemberRepository extends JpaRepository<Member,Long> {

    //자주 쓰이는 거만 정의 해 놨다*****


    List<Member> findByUsernameAndAgeGreaterThan(String username,int age);

    //리포지토리 메소드에 쿼리 정의하기 파라미터 바인딩을 해서 사용
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username,@Param("age")int age);

    //단순 dto 조회  위에는 엔티티 조회 여기는 유저이름인 dto조회
    @Query("select m.username from Member m")
    List<String> findUserName();
    
    //이름 ,team 이름 등 여러개 dto 가져오기 -> dto를 생성해서 가져온다
    @Query("select new study.datajpa.dto.MemberDto(m.id,m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto(); //new 오퍼레이션을 사용 -> 패키지명 다 적기

    //컬렉션 파라미터 바인딩 in 절 사용
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names")Collection<String> names);

    //spring data페이징
    Page<Member> findByAge(int age, Pageable pageable);
    //슬라이스 페이징
    Slice<Member> findSliceByAge(int age, Pageable pageable);

    //spring data페이징인데 카운팅 쿼리를 안보내는 쿼리
    @Query(value = "select m from Member m left join m.team t",
          countQuery = "select count(m.username) from Member m")
        //카운트 쿼리 분리하서 조인 없이 원하는 것만 얻을 수 있다.
    Page<Member>findNoCountByAge(int age,Pageable pageable);
}
