package com.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    @NotBlank(message = "title is required")
    private String title;
    private String description;
    private boolean completed;
}
