package jpabook.jpashop.service;

import jpabook.jpashop.Domain.item.Item;
import jpabook.jpashop.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }


    @Transactional
    public void updateItem(Long itemId, String name,int price,int stockQuantity){
        //준영속 -> 내가 new로 생성했지만 db에 id값이 이미 있는거
        //아이디를 기반으로 영속상태의 아이템을 찾아왔다

        //원래 set말고 findItem에서 change라던지 함수로 빼는게 좋다
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
        //~~ 다 채웠다 치고

        //트렌젹션이 커밋을 하고 
        //flush-> 커밋이 된것 중 값이 바뀐 엔티티를 업데이트함
        //끝 변경 감지되서 알아서 엔티티에서 자동으로 업데이트 된다
        //-> 엔티티의 변경 감지
    }


    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
