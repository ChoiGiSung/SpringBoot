package showping.showpingsample.service;

import showping.showpingsample.Dto.ItemDto;
import java.util.List;

public interface ItemRepository {
    ItemDto save(ItemDto itemDto); //상품등록
    List<ItemDto> findAll(); //상품정보리스트
}
