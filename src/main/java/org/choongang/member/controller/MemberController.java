package org.choongang.member.controller;

import jakarta.validation.Valid;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class MemberController {

    @PostMapping
    public void join(@RequestBody @Valid RequestJoin form, Errors errors) {

    }
}