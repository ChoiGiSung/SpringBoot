package showping.showpingsample.service;

import showping.showpingsample.Dto.OrderDto;
import java.util.List;

//dao에서 정의한거 이용하기
public class OrderService {

    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //주문 등록
    public OrderDto join(OrderDto orderDto){
        //여기서 검사 해당 물품이 item에 있는지
        
        
        orderRepository.save(orderDto);

        return orderDto;
    }

    //주문 조회
    public List<OrderDto> findAll(){
        return orderRepository.findAll();
    }

    //마이너스 업데이트
    public OrderDto minus(OrderDto orderDto){

        return orderRepository.minus(orderDto.getItem_name(),orderDto.getItem_mount());
    }

    //플러스 업데이트
    public OrderDto plus(int order_id,String item_name){
        return orderRepository.plus(order_id,item_name);
    }

    //주문 조회
    public List<OrderDto> search(String user_name, String state){
        return orderRepository.search(user_name,state);
    }
}
