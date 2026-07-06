package org.example.boardprac.exception;

public class DuplicateUserIdException extends RuntimeException{
    public DuplicateUserIdException(String message){
        super(message);
    }
}
