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
            details.put("name", "Song name is required");
        }
        if (song.artist() == null || song.artist().isBlank()) {
            details.put("artist", "Artist is required");
        }
        if (song.album() == null || song.album().isBlank()) {
            details.put("album", "Album is required");
        }
        if (song.year() == null || !song.year().matches(YEAR_REGEX)) {
            details.put("year", "Year must be in a YYYY format");
        }
        if (song.duration() == null || !song.duration().matches(DURATION_REGEX)) {
            details.put("duration", "Duration must be in the format MM:SS");
        }
        if (!details.isEmpty()) {
            throw new InvalidSongDataException("Validation error", details);
        }
    }

    public void validateString(String value, Integer maxLength) {
        if (value == null || value.isEmpty()) {
            throw new InvalidSongDataException("CSV string cannot be null or empty", null);
        }
        if (value.length() > maxLength) {
            throw new InvalidSongDataException(
                    String.format("CSV string is too long: received %s characters, maximum allowed is %s", value.length(), maxLength), null);
        }
    }

    public void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw new InvalidSongDataException(String.format("Invalid ID format: '%s'. Only positive integers are allowed", id), null);
        }
    }

    public void validateId(String id) {
        if (!id.matches(ID_REGEX)) {
            throw new InvalidSongDataException(
                    MessageFormat.format("Invalid ID format: {0}", id), null);
        }
    }
}
