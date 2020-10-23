package jpabook.jpashop.domain.repository;

import jpabook.jpashop.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
public class ItemRepository {
    private final EntityManager entityManager;

    public ItemRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Item item){
        if(item.getId()==null){//처음 등록이면 persist
            entityManager.persist(item);
        }else{
            entityManager.merge(item); //업데이트 비슷함
        }
    }

    public Item findOne(Long id){
        return entityManager.find(Item.class,id);
    }

    public List<Item> findAll(){
        return entityManager.createQuery("select i from Item i",Item.class)
                .getResultList();
    }

}
