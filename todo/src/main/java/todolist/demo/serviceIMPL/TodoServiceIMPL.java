package todolist.demo.serviceIMPL;


import todolist.demo.dto.TodoDto;
import todolist.demo.service.TodoService;

import java.util.*;

public class TodoServiceIMPL {

    private TodoService todoService;
    //impl은 서비스를 받아서 거기의 파인드 올 사용
    //사용할때는 todoservice있어야함 생성자
    public TodoServiceIMPL(TodoService todoService) {
        this.todoService = todoService;
    }

    public List<TodoDto> findTodoAll(){
        return todoService.findAll();
    }


    public String save(TodoDto todoDto){
        return todoService.join(todoDto);
    }

    public String updateTodo(Long id){return todoService.godoing(id);}

    public String udateToDoing(Long id){return todoService.godone(id);}
}
