package org.example.basicboard.exception;

import org.example.basicboard.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    //DuplicateUserIdException 예외 처리
    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<ErrorResponseDto> duplicateUserIdException(DuplicateUserIdException e){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        new ErrorResponseDto(HttpStatus.CONFLICT.value(), e.getMessage())
                );
    }

    //BoardNotFound 예외 처리
    @ExceptionHandler(BoardNotFountException.class)
    public ResponseEntity<ErrorResponseDto> boardNotFountException(BoardNotFountException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponseDto(HttpStatus.NOT_FOUND.value(),e.getMessage())
                );
    }
}
