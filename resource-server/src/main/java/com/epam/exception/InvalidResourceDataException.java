package com.epam.exception;

public class InvalidResourceDataException extends RuntimeException {
  public InvalidResourceDataException(String message) {
    super(message);
  }
}
