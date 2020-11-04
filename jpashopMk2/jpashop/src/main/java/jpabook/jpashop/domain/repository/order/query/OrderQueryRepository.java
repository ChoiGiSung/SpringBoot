package jpabook.jpashop.domain.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager entityManager;


    public List<OrderQueryDto> findOrderQueryDtos() {
        return entityManager.createQuery("select new" +
                " from Order o"+
                " join o.member m"+
                " join o.delivery d",OrderQueryDto.class)
        .getResultList();
    }
}
