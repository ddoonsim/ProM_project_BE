package org.choongang.todolist.service;

import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class TodoNotFoundException extends CommonException {
    public TodoNotFoundException() {
        super(Utils.getMessage("NotFound.todo"), HttpStatus.NOT_FOUND);
    }
}
