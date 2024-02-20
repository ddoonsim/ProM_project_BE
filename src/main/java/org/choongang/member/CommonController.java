package org.choongang.member;

import org.choongang.commons.exceptions.CommonException;
import org.choongang.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("org.choongang.member")
public class CommonController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONData<Object>> errorHandler(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Object message = e.getMessage();

        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();

            if (commonException.getMessages() != null) message = commonException.getMessages();
        } else if (e instanceof BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED; // 401
        } else if (e instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN; // 403
        }
        // BadCredentialsException -> 500 -> 401
        // AccessDeniedException -> 500 -> 403

        JSONData<Object> data = new JSONData<>();
        data.setSuccess(false);
        data.setStatus(status);
        data.setMessage(message);

        e.printStackTrace();

        return ResponseEntity.status(status).body(data);
    }
}
