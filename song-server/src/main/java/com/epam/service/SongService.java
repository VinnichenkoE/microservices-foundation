package com.epam.service;

import com.epam.dto.SongDto;

import java.util.List;
import java.util.Optional;

public interface SongService {

    Integer addSong(SongDto song);

    Optional<SongDto> getSongById(Integer id);

    List<Integer> deleteSongs(String ids);
}
