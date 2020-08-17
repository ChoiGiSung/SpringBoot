package showping.showpingsample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import showping.showpingsample.Dto.ItemDto;
import showping.showpingsample.Dto.ItemDtoForm;
import showping.showpingsample.service.ItemService;
import java.util.List;

@Controller
public class ItemController {


    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item/ItemSign")
    public String ItemSign(){//홈에서 등록 페이지 가기
        return "/item/ItemSign";
    }
    @PostMapping("/item/new")
    public String ItemNew(ItemDtoForm itemDtoForm){//item등록페이지에서 form으로 오는거 상품등록 하겠다
        //상품등록
        ItemDto itemDto=new ItemDto();
        itemDto.setUser_name(itemDtoForm.getUser_name());
        itemDto.setItem_mount(itemDtoForm.getItem_mount());
        itemDto.setItem_price(itemDtoForm.getItem_price());
        itemDto.setItem_name(itemDtoForm.getItem_name());

        itemService.join(itemDto);

        return "redirect:/";
    }

    @GetMapping("/Item/ItemList")
    public String ItemList(Model model){

        List<ItemDto> Items=itemService.findAll();
        model.addAttribute("Items",Items);

        return "/Item/ItemList";
    }

}
