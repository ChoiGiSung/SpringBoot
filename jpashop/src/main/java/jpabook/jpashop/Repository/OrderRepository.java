package jpabook.jpashop.Repository;

import jpabook.jpashop.Domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
        //jpql로 만들자
        //동적 쿼리
        return null;
        
        
        //정적 쿼리
//        return em.createQuery("select o from Order o join o.member m" +
//                "where o.status = :status" +
//                "and m.name like :name", Order.class)
//                .setParameter("status",orderSearch.getOrderStatus())
//                .setParameter("name",orderSearch.getMemberName())
//                .setMaxResults(1000) //1000개까지만 조회 페이지은 퍼스트에서 맥스까지
//                .getResultList();

    }
}
