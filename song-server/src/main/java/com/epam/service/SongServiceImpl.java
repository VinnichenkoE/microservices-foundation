package com.epam.service;

import com.epam.db.entity.SongEntity;
import com.epam.db.repository.SongRepository;
import com.epam.dto.SongDto;
import com.epam.mapper.SongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    private final SongMapper songMapper;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, SongMapper songMapper) {
        this.songRepository = songRepository;
        this.songMapper = songMapper;
    }

    @Override
    public Integer addSong(SongDto song) {
        SongEntity entity = songMapper.toEntity(song);
        entity = songRepository.save(entity);
        return entity.getId();
    }

    @Override
    public Optional<SongDto> getSongById(Integer id) {
        return songRepository.findById(id)
                .map(songMapper::toDto);
    }

    @Override
    public List<Integer> deleteSongs(String ids) {
        return parseCSV(ids).stream()
                .filter(songRepository::existsById)
                .peek(songRepository::deleteById)
                .toList();
    }

    private List<Integer> parseCSV(String csv) {
        return Optional.ofNullable(csv)
                .filter(str -> !str.isBlank())
                .stream()
                .flatMap(str -> Stream.of(str.split(",")))
                .filter(id -> id.matches("\\d+"))
                .map(Integer::parseInt)
                .toList();
    }
}
