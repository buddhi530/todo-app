package com.todo.serviceImpl;


import com.todo.dto.TodoDTO;
import com.todo.entity.Todo;
import com.todo.repository.TodoRepository;
import com.todo.service.TodoService;
import com.todo.utils.CommonResponse;
import com.todo.utils.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public ResponseEntity<CommonResponse> saveToDo(TodoDTO dto) {
        log.info("Start saveToDoImpl method with TodoDTO: " + dto);
        CommonResponse commonResponse = new CommonResponse();
        Optional<Todo> existing = todoRepository.findByTitle(dto.getTitle());
        Todo todo;

        if (existing.isPresent()) {
            todo = existing.get();
            todo.setDescription(dto.getDescription());
            todo.setCompleted(dto.isCompleted());
        } else {
            todo = new Todo(dto.getTitle(), dto.getDescription(), dto.isCompleted());
        }
        todoRepository.save(todo);
        commonResponse.setPayload(Collections.singletonList(todo));
        commonResponse.setStatus(1);

        log.info("End saveToDoImpl method");
        return new ResponseEntity<>(commonResponse,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CommonResponse> getMainTasks(Integer limit) {
                log.info("Start getMainTaskImpl method");
        CommonResponse commonResponse = new CommonResponse();
        List<Todo> toDoList = todoRepository.findMainTasks(limit);
        if (toDoList.isEmpty()){
            commonResponse.setStatus(-1);
            commonResponse.setErrorMessages(Collections.singletonList("Not found todos"));
            return new ResponseEntity<>(commonResponse,HttpStatus.OK);
        }
        commonResponse.setStatus(1);
        commonResponse.setPayload(Collections.singletonList(toDoList));
        log.info("End getMainTaskImpl method");
        return new ResponseEntity<>(commonResponse,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonResponse> completeTask(Integer id) {
        log.info("Start completeTask method");
        CommonResponse commonResponse = new CommonResponse();
        Optional<Todo> existingTodo = todoRepository.findById(id);
        if (!existingTodo.isPresent()) {
            throw new CustomException("Todo with ID " + id + " not found");
        }
        Todo todo = existingTodo.get();
        todo.setCompleted(true);
        Todo dto = todoRepository.save(todo);
        commonResponse.setStatus(1);
        commonResponse.setPayload(Collections.singletonList(todo));
        log.info("End completeTask method");
        return new ResponseEntity<>(commonResponse,HttpStatus.OK);
    }
}
