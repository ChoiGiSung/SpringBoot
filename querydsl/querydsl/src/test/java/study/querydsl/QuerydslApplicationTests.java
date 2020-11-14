package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;

import javax.persistence.EntityManager;

@Transactional
@SpringBootTest
class QuerydslApplicationTests {

	@Autowired
	EntityManager entityManager;

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

}
