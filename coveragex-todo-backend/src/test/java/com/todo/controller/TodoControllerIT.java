package com.todo.controller;

import com.todo.dto.TodoDTO;
import com.todo.entity.Todo;
import com.todo.service.TodoService;
import com.todo.utils.CommonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private TodoDTO todoDTO;

    @BeforeEach
    void setUp() {
        todoDTO = new TodoDTO("Test Task", "Test Description", false);
    }

    @Test
    void testSaveTask() throws Exception {

        Todo todo = new Todo("Test Task", "Test Description", false);
        CommonResponse response = new CommonResponse();
        response.setStatus(1);
        response.setPayload(Collections.singletonList(todo));

        when(todoService.saveToDo(any(TodoDTO.class))).thenReturn(new ResponseEntity<>(response, HttpStatus.CREATED));

        mockMvc.perform(post("/api/v1/todo/createTask")
                        .contentType("application/json")
                        .content("{\"title\":\"Test Task\",\"description\":\"Test Description\",\"completed\":false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(1))
                .andExpect(jsonPath("$.payload[0].title").value("Test Task"))
                .andExpect(jsonPath("$.payload[0].description").value("Test Description"));
    }

    @Test
    void testGetMainTasks() throws Exception {

        Todo todo = new Todo("Test Task", "Test Description", false);
        List<Todo> todoList = Collections.singletonList(todo);
        CommonResponse response = new CommonResponse();
        response.setStatus(1);
        response.setPayload(Collections.singletonList(todoList));

        when(todoService.getMainTasks(5)).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        mockMvc.perform(get("/api/v1/todo/getTasks/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(1))
                .andExpect(jsonPath("$.payload[0][0].title").value("Test Task"))
                .andExpect(jsonPath("$.payload[0][0].description").value("Test Description"));
    }

    @Test
    void testUpdateTask_TaskNotFound() throws Exception {

        when(todoService.completeTask(99)).thenThrow(new RuntimeException("Todo with ID 99 not found"));

        mockMvc.perform(patch("/api/v1/todo/task/complete/99"))
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.status").value(HttpStatus.EXPECTATION_FAILED.value()))
                .andExpect(jsonPath("$.errorMessages[0]").value("Todo with ID 99 not found"));
    }
}
