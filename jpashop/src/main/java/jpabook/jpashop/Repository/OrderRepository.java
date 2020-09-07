package jpabook.jpashop.Repository;

import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class,id);
    }

    //검색기능
    public List<Order> findAllByString(OrderSearch orderSearch){
        //쿼리dsl로 만들자
        //동적 쿼리
        //근데 JPA Criteria만 알려줘서 그거쓰겠다
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" +
                            orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대1000건
        return query.getResultList();

        //정적 쿼리
//        return em.createQuery("select o from Order o join o.member m" +
//                "where o.status = :status" +
//                "and m.name like :name", Order.class)
//                .setParameter("status",orderSearch.getOrderStatus())
//                .setParameter("name",orderSearch.getMemberName())
//                .setMaxResults(1000) //1000개까지만 조회 페이지은 퍼스트에서 맥스까지
//                .getResultList();

    }

    //api위해 생성 주문조회 v3 조인 패치 사용 한반쿼리
    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o"+
                        " join fetch o.member m"+
                        " join fetch o.delivery d",Order.class
        ).getResultList();
    }

    //v4 주문조회
    public List<OrderSimpleQueryDto> findOrderDtos() {
      return em.createQuery("select o from Order o"+
                " join o.member m"+
                " join o.delivery d",OrderSimpleQueryDto.class
                ).getResultList();
    }
}
