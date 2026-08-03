package org.example.oauth2.exception;

public class DuplicateUserIdException extends RuntimeException{

    public DuplicateUserIdException(String message) {
        super(message);
    }
}
