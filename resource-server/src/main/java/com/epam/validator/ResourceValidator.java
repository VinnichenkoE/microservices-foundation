package com.epam.validator;

import com.epam.exception.InvalidResourceDataException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class ResourceValidator {

    private static final String ID_REGEX = "\\d+";

    public void validateString(String value, Integer maxLength) {
        if (value == null || value.isEmpty()) {
            throw new InvalidResourceDataException("Value cannot be null or empty");
        }
        if (value.length() > maxLength) {
            throw new InvalidResourceDataException(
                    MessageFormat.format("Value length: {0} exceeds maximum length of: {1} ", value.length(), maxLength)
            );
        }
    }

    public void validateId(String id) {
        if (!id.matches(ID_REGEX)) {
            throw new InvalidResourceDataException(
                    MessageFormat.format("Invalid ID format: {0}", id));
        }
    }
}
