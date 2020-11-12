package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    //벌크성 쿼리 -> 여러개의 값 변경 (db의 값을 바꾸자)
    @Modifying(clearAutomatically = true) //flust 랑 clear를  해준다.
    @Query("update Member m set m.age= m.age+1 where m.age>= :age")
    int bulkAgePlus(@Param("age")int age);

    //패치조인 -> 한방쿼리
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    //패치조인을 스프링데이터jpa에서는 엔티티그래프라는
    //것으로 제공한다
    @Override//-> 페치 조인과 같은 효과
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    //내가 만든거도 있는데 페치조인 살짝 넣고 싶어
    @Query
    @EntityGraph(attributePaths = {"team"})
    List<Member> findMemberEntityGraph();

    //메서드 이름으로 하는거에 패치 조인 넣기
    @EntityGraph(attributePaths = {"team"})
    List<Member>findEntityByUsername(@Param("username")String username);
}
