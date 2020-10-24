package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public void saveItem(Item item){
        itemRepository.save(item);
    }
    @Transactional(readOnly = true)
    public List<Item> findItems(){
        return itemRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

    //업데이트 변경감지 만들기
    public void updateItem(Long id,String name,int price,int stockQuantity){
        Item findItem = itemRepository.findOne(id);
        findItem.change(id,name,price,stockQuantity);
        //엔티티 쪽에 비지니스 로직을 만드는게 유지보수에 좋다
    }
}
