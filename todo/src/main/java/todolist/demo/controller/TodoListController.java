package todolist.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import todolist.demo.dao.TodoDao;
import todolist.demo.dto.TodoDto;
import todolist.demo.dto.TodoDtoForm;
import todolist.demo.service.TodoService;
import todolist.demo.serviceIMPL.TodoServiceIMPL;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@Controller
public class TodoListController {

//    TodoDao todoDao;

//    public TodoListController(TodoDao todoDao) {
//        this.todoDao = todoDao;
//    }

    private Logger log= LoggerFactory.getLogger(TodoListController.class);
    TodoServiceIMPL todoServiceIMPL;

    public TodoListController(TodoServiceIMPL todoServiceIMPL) {
        this.todoServiceIMPL = todoServiceIMPL;
    }

    @GetMapping("/")
    public String home(Model model){
        List<TodoDto> todo= todoServiceIMPL.findTodoAll();
        model.addAttribute("todo",todo);
        return "/TodoList/Home";
    }

    @GetMapping("/TodoList/write")
    public String write(Model model){
        return "/TodoList/write";
    }

    @PostMapping("/TodoList/new")
    public String join(TodoDtoForm todoDtoForm){
        TodoDto todoDto=new TodoDto();

        todoDto.setTitle(todoDtoForm.getTitle());
        todoDto.setName(todoDtoForm.getName());
        todoDto.setSequence(todoDtoForm.getSequence());
        todoDto.setType("todo");
        todoDto.setRegdate(new Date());

        todoServiceIMPL.save(todoDto);

        return "redirect:/";
    }

    @GetMapping("/TodoList/godoing")
    public String godoing(@RequestParam(name = "id")Long id){

        log.info(id.toString());
        todoServiceIMPL.updateTodo(id);

        return "redirect:/";
    }

    @GetMapping("/TodoList/godone")
    public String godone(@RequestParam(name="id")Long id){

        todoServiceIMPL.udateToDoing(id);
        return "redirect:/";

    }



}
