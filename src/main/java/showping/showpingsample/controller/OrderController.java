package showping.showpingsample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import showping.showpingsample.Dto.*;
import showping.showpingsample.service.ItemService;
import showping.showpingsample.service.OrderService;

import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    private OrderService orderService;

    //상품주문할떄 상품명을 보기위해
    private ItemService itemService;


    public OrderController(OrderService orderService,ItemService itemService) {
        this.orderService = orderService;
        this.itemService=itemService;
    }

    //로그 찍기
    private Logger log= LoggerFactory.getLogger(OrderController.class);





    @GetMapping("/order/orderSign")//홈에서 주문페이지 가기
    public String orderPage(Model model){

        //상품 주문할때 물건을 보기위해 항목
        List<ItemDto> items= itemService.findAll();
        model.addAttribute("Items",items);

        return "/order/OrderSign";
    }



    @PostMapping("/order/new")//주문페이지에서 오는 form
    @Transactional(readOnly = false)
    public String orderSign(OrderDtoForm orderDtoForm){
        //주문 등록
        OrderDto orderDto=new OrderDto();
        orderDto.setUser_name(orderDtoForm.getUser_name());
        orderDto.setItem_name(orderDtoForm.getItem_name());
        orderDto.setItem_mount(orderDtoForm.getItem_mount());
        orderDto.setOrder_state("order");
        orderDto.setOrder_date(new Date());

        //조인하면서 업데이트

        orderService.join(orderDto);
        orderService.minus(orderDto);




        return "redirect:/";
    }

    @GetMapping("/order/OrderList")
    public String orderList(Model model){

        List<OrderDto> orders= orderService.findAll();
        model.addAttribute("Orders",orders);

        return "/order/OrderList";
    }

    @GetMapping("/order/cancle")
    public String orderCancle(@RequestParam (value ="order_id") String order_id,
                              @RequestParam (value = "item_name")String item_name,
                                      Model model){ //cancle누르면 오는곳
        //리스트에서 model로 준값을 list에서 다시 cancle로 보낸다

        int id=Integer.parseInt(order_id);
        //플러스 서비스호출
        orderService.plus(id,item_name);



        //리스트 열기위한 모델 선언
        List<OrderDto> orders= orderService.findAll();
        model.addAttribute("Orders",orders);

        return "/order/OrderList";
    }

    //검색시 오는곳
    @PostMapping("/order/state")
    public String Search(OrderSearchDtoForm OrderSearchDtoForm,
                         Model model){

        log.info(OrderSearchDtoForm.getUser_name());
        log.info(OrderSearchDtoForm.getState());

        List<OrderDto> orderSearch= orderService.search(OrderSearchDtoForm.getUser_name(),OrderSearchDtoForm.getState());
        model.addAttribute("Orders",orderSearch);

        return "/order/OrderList";
    }
}
