package org.choongang.subtask.controllers;

import jakarta.validation.constraints.NotBlank;
import org.choongang.member.entities.Member;

import java.util.List;

public record RequestSubtaskForm(
        List<Member> member,
        @NotBlank
        String title,
        String content
) {
}
