package showping.showpingsample.Dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;
import showping.showpingsample.Dto.OrderDto;
import showping.showpingsample.controller.OrderController;
import showping.showpingsample.service.OrderRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//dao의 리턴값은 service갔다가 controller로 간다
public class OrderDao implements OrderRepository {

    private Logger log= LoggerFactory.getLogger(OrderDao.class);

    private JdbcTemplate jdbcTemplate;
    public OrderDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //주문 넣기
    @Override
    public OrderDto save(OrderDto orderDto) {
       //인서트문 넣고
        SimpleJdbcInsert jdbcInsert=new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("order_table").usingGeneratedKeyColumns("order_id");

        //값을 보내기위한 map
        Map<String,Object> parameters=new HashMap<>();
        parameters.put("user_name",orderDto.getUser_name());
        parameters.put("item_name",orderDto.getItem_name());
        parameters.put("item_mount",orderDto.getItem_mount());
        parameters.put("order_state",orderDto.getOrder_state());
        parameters.put("order_date",orderDto.getOrder_date());

        Number key=jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        orderDto.setOrder_id(key.longValue());

        return orderDto;
    }

    @Override
    public List<OrderDto> findAll() {
        return jdbcTemplate.query("select * from order_table",orderDtoRowMapper());
    }

    //재고가 마이너스
    @Override
    public OrderDto minus(String item_name,int order_mount) {
        //여기서 업데이트
        jdbcTemplate.update("update item set item_mount = (select (i.item_mount- ? ) from item i where i.item_name = ?) where item_name= ?",order_mount,item_name,item_name);

        return null;
    }

    //재고 플러스
    @Override
    public OrderDto plus(int order_id,String item_name) { //업데이트문에 필요한건 아이템이름과 재고
        //업데이트문 작성
        //cancle누른거의 아이템이름과 해당 갯수를 가지고 와서 item테이블에 +시킨다



        jdbcTemplate.update("update item set item_mount = " +
                " (select distinct (i.item_mount + o.item_mount )" +
                " from order_table o,item i" +
                " where o.order_id = ? " +
                "and i.item_name = ?)" +
                "" +
                "where item_name=?",order_id,item_name,item_name);

        //버튼상태 바꾸기
        jdbcTemplate.update("update order_table set order_state= 'cancle' where order_id=?",order_id);
        return null;
    }

    @Override
    public List<OrderDto> search(String user_name,String state) {
        if(!user_name.isEmpty() &&!state.isEmpty()){
            //둘다 이름이 있어
            log.info("orderDao"+user_name+state);
            return jdbcTemplate.query("select o.* \n" +
                    "from order_table o \n" +
                    "where o.user_name=?\n" +
                    "and o.order_state=?",orderDtoRowMapper(),user_name,state);
        }else if(user_name.isEmpty() && !state.isEmpty()){
            //상태의 이름만 있어
            return jdbcTemplate.query("select o.* \n" +
                    "from order_table o \n" +
                    "where o.order_state=?",orderDtoRowMapper(),state);
        }else if(!user_name.isEmpty() && state.isEmpty()){
            //주문자의 이름만 있어
            return jdbcTemplate.query("select o.* \n" +
                    "from item i,order_table o \n" +
                    "where o.user_name=?",orderDtoRowMapper(),user_name);
        }

        return jdbcTemplate.query("select * from order_table",orderDtoRowMapper());
    }

    private RowMapper<OrderDto> orderDtoRowMapper(){
        return new RowMapper<OrderDto>() {
            @Override
            public OrderDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrderDto orderDto=new OrderDto();
                //order_id를 안해서 여태 list html에서 order_id를 볼 수 없었다
                orderDto.setOrder_id(rs.getLong("order_id"));
                orderDto.setUser_name(rs.getString("user_name"));
                orderDto.setItem_name(rs.getString("item_name"));
                orderDto.setItem_mount(rs.getInt("item_mount"));
                orderDto.setOrder_state(rs.getString("order_state"));
                orderDto.setOrder_date(rs.getDate("order_date"));

                return orderDto;
            }
        };
    }
}
