package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;

@Transactional
@SpringBootTest
class QuerydslApplicationTests {

	@Autowired
	EntityManager entityManager;

	JPAQueryFactory queryFactory=new JPAQueryFactory(entityManager);
	@Test
	void contextLoads() {

		Member member=new Member("userA");
		entityManager.persist(member);

		JPAQueryFactory queryFactory=new JPAQueryFactory(entityManager);
		QMember qMember = new QMember("m");

		Member result = queryFactory
				.selectFrom(qMember)
				.fetchOne();

		Assertions.assertEquals(result,member);
	}

	@BeforeEach
	public void init(){
		Team teamA=new Team("teamA");
		Team teamB=new Team("teamB");
		entityManager.persist(teamA);
		entityManager.persist(teamB);

		Member member1=new Member("member1",10,teamA);
		Member member2=new Member("member2",20,teamB);

		entityManager.persist(member1);
		entityManager.persist(member2);
	}
	 @Test
	 public  void  startJPQL(){
		 Member findByJPQL = entityManager.createQuery("select m from Member m " +
				 "where m.username = :username", Member.class)
				 .setParameter("username", "member1")
				 .getSingleResult();
		 Assertions.assertEquals(findByJPQL.getUsername(),"member1");
	 }

	 @Test
	public void startQuerydsl(){

		 QMember m = new QMember("m");
		//QMember mm=QMember.member;

		 Member findM = queryFactory
				 .select(m)
				 .from(m)
				 .where(m.username.eq("member1"))
				 .fetchOne();
		 Assertions.assertEquals(findM.getUsername(),"member1");
	 }

	 @Test
	public void search(){
		 Member findMember = queryFactory
				 .selectFrom(QMember.member)
				 .where(QMember.member.username.eq("member1")
						 .and(QMember.member.age.eq(10)))
				 .fetchOne();
		Assertions.assertEquals(findMember.getUsername(),"member1");
	 }



}
