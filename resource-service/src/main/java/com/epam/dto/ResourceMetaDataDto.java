package com.epam.dto;

public record ResourceMetaDataDto(
        Integer id,
        String name,
        String artist,
        String album,
        String duration,
        String year
) {
}
