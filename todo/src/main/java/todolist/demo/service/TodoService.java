package todolist.demo.service;

import todolist.demo.dto.TodoDto;

import java.util.*;

public interface TodoService {
    List<TodoDto> findAll(); //전부 찾기
    String join(TodoDto todoDto);   //등록
    String godoing(Long id);//todㅇ -> doing으로 업데이트
    String godone(Long id);//doing -> done으로 업데이트
}
