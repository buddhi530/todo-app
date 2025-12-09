package com.todo.service;



import com.todo.dto.TodoDTO;
import com.todo.utils.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TodoService {

//    ResponseEntity<CommonResponse> getAllDepartment();
//    ResponseEntity<CommonResponse> getAllDesignation();

    ResponseEntity<CommonResponse> saveToDo(TodoDTO dto);

    ResponseEntity<CommonResponse> getMainTasks(Integer limit);

    ResponseEntity<CommonResponse> completeTask(Integer id);
}
