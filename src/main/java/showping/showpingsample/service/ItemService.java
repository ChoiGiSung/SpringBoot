package showping.showpingsample.service;

import showping.showpingsample.Dto.ItemDto;


import java.util.List;

public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    //상품등록
    public Long join(ItemDto itemDto){
        itemRepository.save(itemDto);

        return itemDto.getItem_id();
    }

    //전체 상품 조회
    public List<ItemDto> findAll(){
        return itemRepository.findAll();
    }
}
