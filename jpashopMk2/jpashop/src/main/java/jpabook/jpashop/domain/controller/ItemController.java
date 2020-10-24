package jpabook.jpashop.domain.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){
        Book book=new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStocQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());;
        book.setIsbn(form.getIsbn());
        //이거보다는 크리에이트 book 생성메서드를 만드는게 좋다
        //setter를 다 날리고

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemList";
    }


    //수정 누르면
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId")Long itemId,Model model){
        Book item = (Book)itemService.findOne(itemId);

        BookForm form=new BookForm();
        form.setId(item.getId());
        form.setAuthor(item.getAuthor());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStocQuantity());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form",form);
        return "items/updateItemForm";

    }

    //수정 완료 버튼 누르면
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form){

        //아이디가 넘어오는데 누가 변경할 수 있으니
        //권한을 체크해야 한다

//       Book book=new Book();
//        book.setId(form.getId());
//        book.setAuthor(form.getAuthor());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStocQuantity(form.getStockQuantity());
//        book.setIsbn(form.getIsbn());
//
//        itemService.saveItem(book);
        //이렇게 하는게 유지보수에 좋다
        //웹 계층에서 어설프게 엔티티를 생성하지 맣고 위임하자
        itemService.updateItem(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }
}
