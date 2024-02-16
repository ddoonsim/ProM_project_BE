package org.choongang.project.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.choongang.member.entities.Member;

import java.util.List;

@Builder
public record RequestProjectForm(
        List<Member> member,
        @NotBlank
        String pName,
        String description
) {}
