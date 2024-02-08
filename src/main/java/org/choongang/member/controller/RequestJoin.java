package org.choongang.member.controller;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RequestJoin(

        @NotBlank @Email
        String email,

        @NotBlank @Size(min=8)
        String password,

        @NotBlank
        String confirmPassword,

        @NotBlank
        String name,

        String mobile,

        @AssertTrue
        Boolean agree
) {
}
