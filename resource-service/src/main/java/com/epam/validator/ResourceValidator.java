package com.epam.validator;

import com.epam.exception.InvalidResourceDataException;
import org.springframework.stereotype.Component;

@Component
public class ResourceValidator {

    private static final String ID_REGEX = "\\d+";

    public void validateString(String value, Integer maxLength) {
        if (value == null || value.isEmpty()) {
            throw new InvalidResourceDataException("CSV string cannot be null or empty");
        }
        if (value.length() > maxLength) {
            throw new InvalidResourceDataException(
                    String.format("CSV string is too long: received %s characters, maximum allowed is %s", value.length(), maxLength));
        }
    }

    public void validateId(String id) {
        if (!id.matches(ID_REGEX)) {
            throw new InvalidResourceDataException(
                    String.format("Invalid ID format: '%s'. Only positive integers are allowed", id));
        }
    }

    public void validateId(Integer id) {
        if (id <= 0) {
            throw new InvalidResourceDataException(
                   String.format("Invalid value '%d' for ID. Must be a positive integer", id));
        }
    }
}
