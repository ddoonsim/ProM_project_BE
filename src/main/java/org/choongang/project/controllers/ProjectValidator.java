package org.choongang.project.controllers;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProjectValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestProjectForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestProjectForm form = (RequestProjectForm) target;
    }
}
