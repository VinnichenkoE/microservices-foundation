package com.epam.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSongNotFoundException(SongNotFoundException exception) {
        return buildErrorResponse(exception.getMessage(), null, "404", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidSongDataException.class)
    public ResponseEntity<ErrorResponse> handleSongNotFoundException(InvalidSongDataException exception) {
        return buildErrorResponse(exception.getMessage(), exception.getDetails(), "400", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SongAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleSongAlreadyExistsException(SongAlreadyExistsException exception) {
        return buildErrorResponse(exception.getMessage(), null, "409", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
        return buildErrorResponse(exception.getMessage(), null, "500", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return buildErrorResponse("Unsupported Media Type", null, "400", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("Argument type mismatch for parameter '%s': %s", ex.getName(), ex.getValue());
        return buildErrorResponse(errorMessage, null, "400", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, Map<String, String> details, String code, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(message, details, code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(errorResponse, headers, status);
    }
}
