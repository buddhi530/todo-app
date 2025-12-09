package com.todo.repository;



import com.todo.entity.Todo;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer> {
    Optional<Todo> findByTitle(String title);

    @Query(value = "select * from task where completed = 0 order by createdDate desc limit ?1",nativeQuery = true)
    List<Todo> findMainTasks(Integer limit);

//    Department findByDescription(String description);
//
//    @Query(value = "SELECT u FROM Department u WHERE u.status = 1",nativeQuery = true)
//    List<Department> findAllActiveDepartmentNative();
}
