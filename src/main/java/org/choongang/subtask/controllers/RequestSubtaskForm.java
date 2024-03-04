package org.choongang.subtask.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.choongang.todolist.controllers.RequestTodo;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Builder
public record RequestSubtaskForm(
        Long seq,
        List<Long> members,
        String gid,
        Long pSeq,
        String status,
        @NotBlank
        String tname,
        String description,
        @DateTimeFormat(pattern="yyyy-MM-dd")
        LocalDate sdate,
        @DateTimeFormat(pattern="yyyy-MM-dd")
        LocalDate edate,

        List<RequestTodo> todos
) {
}
