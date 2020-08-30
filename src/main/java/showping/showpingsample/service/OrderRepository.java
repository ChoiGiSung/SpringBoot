package showping.showpingsample.service;

import showping.showpingsample.Dto.OrderDto;
import java.util.List;

public interface OrderRepository {
    OrderDto save(OrderDto orderDto); //오더 저장하기
    List<OrderDto> findAll(); //오더 리스트
    OrderDto minus(String item,int mount);//오더의 주문수량 빼기
    OrderDto plus(int order_id,String item_name);//오더의 주문수량 더하기
    List<OrderDto> search(String user_name,String state);//검색 기능
}
