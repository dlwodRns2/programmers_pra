package org.example.securityprac.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class DuplicateUserIdException extends RuntimeException {
    private HttpStatus httpStatus;

    public DuplicateUserIdException(HttpStatus httpStatus,String message) {
        super(message);
        this.httpStatus=httpStatus;
    }
}
