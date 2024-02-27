package org.choongang.subtask.controllers;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SubtaskValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestSubtaskForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestSubtaskForm form = (RequestSubtaskForm) target;
    }
}
