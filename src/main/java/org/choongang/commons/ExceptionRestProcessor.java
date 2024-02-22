package org.choongang.commons;

import org.choongang.commons.exceptions.CommonException;
import org.choongang.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface ExceptionRestProcessor {
    @ExceptionHandler(Exception.class)
    default ResponseEntity<JSONData<Object>> errorHandler(Exception e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException)e;
            status = commonException.getStatus();
        } else if (e instanceof BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (e instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        }

        JSONData<Object> data = new JSONData<>();
        data.setSuccess(false);
        data.setStatus(status);
        data.setMessage(e.getMessage());

        e.printStackTrace();
        System.out.println("유입?");
        System.out.println(data);
        return ResponseEntity.status(status).body(data);
    }
}
