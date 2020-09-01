package todolist.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import todolist.demo.controller.TodoListController;
import todolist.demo.dao.TodoDao;
import todolist.demo.service.TodoService;
import todolist.demo.serviceIMPL.TodoServiceIMPL;

import javax.sql.DataSource;

@Configuration
public class TodoConfig {

    private DataSource dataSource;

    @Autowired
    public TodoConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Bean
    public TodoServiceIMPL todoServiceIMPL(){
        return new TodoServiceIMPL(todoService());
    }

   @Bean
   public TodoService  todoService(){
        return new TodoDao(dataSource);
   }


}
