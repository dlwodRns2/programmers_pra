package org.example.boardservice.exception;

public class DuplicateUserIdException extends RuntimeException {
  public DuplicateUserIdException(String message) {
    super(message);
  }
}
