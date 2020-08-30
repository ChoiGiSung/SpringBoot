package showping.showpingsample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import showping.showpingsample.Dao.ItemDao;
import showping.showpingsample.Dao.OrderDao;
import showping.showpingsample.Dao.UserDao;
import showping.showpingsample.Dto.OrderDto;
import showping.showpingsample.service.*;

import javax.sql.DataSource;

@Configuration
public class ShowpingConfig {

    private DataSource dataSource;

    @Autowired
    public ShowpingConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    //회원가입
    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }
    @Bean
    public UserRepository userRepository(){
        return new UserDao(dataSource);
    }

    //상품등록
    @Bean
    public ItemService itemService(){return new ItemService(itemRepository());}
    @Bean
    public ItemRepository itemRepository(){return new ItemDao(dataSource);
    }

    //주문등록
    @Bean
    public OrderService orderService(){
        return new OrderService(orderRepository());
    }
    @Bean //리포지토리는 서비스에서 사용한다
    public OrderRepository orderRepository(){
        return new OrderDao(dataSource);
    }

}
