package com.epam.exception;

public class SongAlreadyExistsException extends RuntimeException {

    public SongAlreadyExistsException(String message) {
        super(message);
    }
}
