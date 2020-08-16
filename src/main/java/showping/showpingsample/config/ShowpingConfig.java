package showping.showpingsample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import showping.showpingsample.Dao.UserDao;
import showping.showpingsample.service.UserRepository;
import showping.showpingsample.service.UserService;

import javax.sql.DataSource;

@Configuration
public class ShowpingConfig {

    private DataSource dataSource;

    @Autowired
    public ShowpingConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }

    @Bean
    public UserRepository userRepository(){
        return new UserDao(dataSource);
    }
}
