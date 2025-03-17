package com.epam.mapper;

import com.epam.db.entity.SongEntity;
import com.epam.dto.SongDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {

    SongEntity toEntity(SongDto songDto);

    SongDto toDto(SongEntity songEntity);
}
