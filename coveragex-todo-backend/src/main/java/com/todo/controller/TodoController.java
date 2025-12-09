package com.todo.controller;




import com.todo.dto.TodoDTO;
import com.todo.service.TodoService;
import com.todo.utils.CommonResponse;
import com.todo.utils.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/api/v1/todo")
public class TodoController {

    private TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    /**
     * Creates a new Todo task.
     *
     * @param dto Task details.
     * @return ResponseEntity with status and result.
     */
    @PostMapping("/createTask")
    public ResponseEntity<CommonResponse> saveTodoTask(@RequestBody TodoDTO dto){
        log.info("Start saveTodoTask method with TodoDTO: " + dto);
        ResponseEntity<CommonResponse> responseEntity = null;
        CommonResponse commonResponse = new CommonResponse();
        try {
            responseEntity = service.saveToDo(dto);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        log.info("End saveTodoTask method");
        return responseEntity;
    }

    /**
     * Retrieves a list of Todo tasks with a limit.
     *
     * @param limit Max number of tasks to retrieve.
     * @return ResponseEntity with tasks and status.
     */
    @GetMapping("/getTasks/{limit}")
    public ResponseEntity<CommonResponse> getMainTasks(@PathVariable("limit") Integer limit){
        log.info("Start getMainTasks method");
        ResponseEntity<CommonResponse> responseEntity = null;
        CommonResponse commonResponse = new CommonResponse();
        try {
            responseEntity = service.getMainTasks(limit);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        log.info("End getMainTasks method");
        return responseEntity;
    }

    /**
     * Marks a Todo task as complete.
     *
     * @param id Task ID to complete.
     * @return ResponseEntity with status of the update.
     */
    @PatchMapping("/task/complete/{id}")
    public ResponseEntity<CommonResponse> updateTask(@PathVariable("id") Integer id){
        log.info("Start updateTask method");
        ResponseEntity<CommonResponse> responseEntity = null;
        CommonResponse commonResponse = new CommonResponse();
        try {
            responseEntity = service.completeTask(id);
        }catch (CustomException ex){
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        log.info("End updateTask method");
        return responseEntity;
    }

}

