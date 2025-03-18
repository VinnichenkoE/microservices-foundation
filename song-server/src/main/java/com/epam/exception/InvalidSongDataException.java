package com.epam.exception;

import java.util.Map;

public class InvalidSongDataException extends RuntimeException {

    private final Map<String, String> details;

    public InvalidSongDataException(String message, Map<String, String> details) {
        super(message);
        this.details = details;
    }

    public Map<String, String> getDetails() {
        return details;
    }
}
