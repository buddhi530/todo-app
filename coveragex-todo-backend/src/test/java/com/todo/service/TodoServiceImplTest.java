package com.todo.service;

import com.todo.dto.TodoDTO;
import com.todo.entity.Todo;
import com.todo.repository.TodoRepository;
import com.todo.serviceImpl.TodoServiceImpl;
import com.todo.utils.CommonResponse;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Optional;

public class TodoServiceImplTest {
    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private TodoDTO todoDTO;

    private Todo todo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        todoDTO = new TodoDTO("Test Task", "Test Description", false);
        todo = new Todo("Test Task", "Test Description", false);
    }

    @Test
    void testSaveToDo_NewTask() {
        when(todoRepository.findByTitle(todoDTO.getTitle())).thenReturn(Optional.empty());

        ResponseEntity<CommonResponse> response = todoService.saveToDo(todoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getPayload());
        assertEquals(1, response.getBody().getStatus());
    }

    @Test
    void testSaveToDo_ExistingTask() {

        Todo existingTodo = new Todo("Test Task", "Old Description", false);
        when(todoRepository.findByTitle(todoDTO.getTitle())).thenReturn(Optional.of(existingTodo));

        ResponseEntity<CommonResponse> response = todoService.saveToDo(todoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test Description", existingTodo.getDescription());  // Assert that the description is updated
    }

    @Test
    void testGetMainTasks_EmptyList() {

        when(todoRepository.findMainTasks(5)).thenReturn(Collections.emptyList());

        ResponseEntity<CommonResponse> response = todoService.getMainTasks(5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(-1, response.getBody().getStatus());
        assertTrue(response.getBody().getErrorMessages().contains("Not found todos"));
    }

    @Test
    void testGetMainTasks_WithRecentTasks() {

        Todo todo = new Todo("Test Task", "Description", false);
        List<Todo> todoList = Collections.singletonList(todo);
        when(todoRepository.findMainTasks(5)).thenReturn(todoList);

        ResponseEntity<CommonResponse> response = todoService.getMainTasks(5);

        List<Todo> actualPayload =  (List<Todo>)response.getBody().getPayload().get(0);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getStatus());
        assertEquals(todoList,actualPayload);
    }

    @Test
    void testCompleteTask_Success() {

        when(todoRepository.findById(1)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        ResponseEntity<CommonResponse> response = todoService.completeTask(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getStatus());
        assertTrue(response.getBody().getPayload().contains(todo));
        assertTrue(todo.isCompleted());
    }
}
