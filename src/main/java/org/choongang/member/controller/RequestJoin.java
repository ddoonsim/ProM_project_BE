package org.choongang.member.controller;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RequestJoin(

        String gid,

        @NotBlank @Email
        String email,

        @NotBlank @Size(min=8)
        String password,

        @NotBlank
        String confirmPassword,

        @NotBlank
        String name,

        @NotBlank
        String mobile,

        @AssertTrue
        Boolean agree
) {
}
