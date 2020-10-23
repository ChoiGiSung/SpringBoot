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
}
