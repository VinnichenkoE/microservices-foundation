package com.epam.dto;

public record SongDto(
        Integer id,
        String name,
        String artist,
        String album,
        String duration,
        String year
) {
}
