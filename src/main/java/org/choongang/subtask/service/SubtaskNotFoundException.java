package org.choongang.subtask.service;

import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class SubtaskNotFoundException extends CommonException {
    public SubtaskNotFoundException() {
        super(Utils.getMessage("NotFound.subtask"), HttpStatus.NOT_FOUND);
    }
}
