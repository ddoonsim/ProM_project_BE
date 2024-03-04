package org.choongang.todolist.controllers;

import jakarta.validation.constraints.NotBlank;

public record RequestTodo(
        Long seq, // Todolist 등록번호
        Long sSeq, // Subtask 등록번호,
        @NotBlank
        String content,
        boolean done
) {}
