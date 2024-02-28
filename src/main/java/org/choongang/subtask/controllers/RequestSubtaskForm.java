package org.choongang.subtask.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.choongang.member.entities.Member;

import java.util.List;

@Builder
public record RequestSubtaskForm(
        List<Long> member,
        @NotBlank
        String gid,
        Long pSeq,
        String status,
        @NotBlank
        String tName,
        String description,
        String sDate,
        String eDate

) {
}
