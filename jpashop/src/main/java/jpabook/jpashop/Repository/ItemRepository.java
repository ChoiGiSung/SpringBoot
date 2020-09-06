package jpabook.jpashop.Repository;

import jpabook.jpashop.Domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        //merge를 호출하는게 병합 사용이다
        //준영속 상태의 엔티티를 영속 상태로 바꾸는 것
        //값을 다 바꿔치기 한다 -> 트렌젝션 커밋될때 업데이트 된다

        //변경감지와 병합의 차이점 ->
        //병합은 값을 다 바꾼다 변경감지는 내가 원하는 값만 바꾼다
        //그래서 병합시 값이 없으면 null을 반환할 수 있다
        //ex) item의 price는 한번 결정하면 변경할 수 없다고 하면
        // db에 올라갈땐 null로 올라간다 그래서 변경감지를 사용해라
        if (item.getId() == null) {
            //처음은 아무것도 들어있지 않으니 null이면 등록시킨다
            em.persist(item);
        } else {
            //있으면 업데이트
            Item merge = em.merge(item);
            //item이 영속성이 되는 것이 아닌 merge가 영속성이되는 것이다
            //item은 계속 준영속성에 머물러 있다
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
