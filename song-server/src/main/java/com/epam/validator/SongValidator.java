package com.epam.validator;

import com.epam.dto.SongDto;
import com.epam.exception.InvalidSongDataException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Component
public class SongValidator {

    private static final String YEAR_REGEX = "^(19[0-9]{2}|20[0-9]{2})$";

    private static final String DURATION_REGEX = "^[0-5][0-9]:[0-5][0-9]$";

    private static final String ID_REGEX = "\\d+";

    public void validate(SongDto song) {
        Map<String, String> details = new HashMap<>();
        if (song.name() == null || song.name().isBlank()) {
            details.put("name", "Name must not be null or blank");
        }
        if (!song.year().matches(YEAR_REGEX)) {
            details.put("year", "Year must be between 1900 and 2099 and must not start with a leading zero");
        }
        if (!song.duration().matches(DURATION_REGEX)) {
            details.put("duration", "Duration must be in the format mm:ss and valid");
        }
        if (!details.isEmpty()) {
            throw new InvalidSongDataException("Invalid song data", details);
        }
    }

    public void validateString(String value, Integer maxLength) {
        if (value == null || value.isEmpty()) {
            throw new InvalidSongDataException("Value cannot be null or empty", null);
        }
        if (value.length() > maxLength) {
            throw new InvalidSongDataException(
                    MessageFormat.format("Value length: {0} exceeds maximum length of: {1}", value.length(), maxLength), null);
        }
    }

    public void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw new InvalidSongDataException("Song id should be positive", null);
        }
    }

    public void validateId(String id) {
        if (!id.matches(ID_REGEX)) {
            throw new InvalidSongDataException(
                    MessageFormat.format("Invalid ID format: {0}", id), null);
        }
    }
}
