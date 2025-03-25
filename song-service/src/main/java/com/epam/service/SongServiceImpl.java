package com.epam.service;

import com.epam.db.entity.SongEntity;
import com.epam.db.repository.SongRepository;
import com.epam.dto.DeleteResponseDto;
import com.epam.dto.SongDto;
import com.epam.dto.UploadResponseDto;
import com.epam.exception.SongAlreadyExistsException;
import com.epam.exception.SongNotFoundException;
import com.epam.mapper.SongMapper;
import com.epam.validator.SongValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class SongServiceImpl implements SongService {

    private static final int MAX_ID_LENGTH = 199;

    private final SongRepository songRepository;

    private final SongMapper songMapper;

    private final SongValidator songValidator;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, SongMapper songMapper, SongValidator songValidator) {
        this.songRepository = songRepository;
        this.songMapper = songMapper;
        this.songValidator = songValidator;
    }

    @Override
    public UploadResponseDto addSong(SongDto song) {
        songValidator.validate(song);
        SongEntity entity = songMapper.toEntity(song);
        Integer id = entity.getId();
        if (songRepository.existsById(id)) {
            throw new SongAlreadyExistsException(
                    MessageFormat.format("Song with id: {0} already exists", String.valueOf(id)));
        }
        entity = songRepository.save(entity);
        return new UploadResponseDto(entity.getId());
    }

    @Override
    public SongDto getSongById(Integer id) {
        songValidator.validateId(id);
        return songRepository.findById(id)
                .map(songMapper::toDto)
                .orElseThrow((() -> new SongNotFoundException(
                        MessageFormat.format("Song with id: {0} not found", String.valueOf(id)))));
    }

    @Override
    public DeleteResponseDto deleteSongs(String ids) {
        songValidator.validateString(ids, MAX_ID_LENGTH);
        List<Integer> deletedIds = parseCSV(ids).stream()
                .filter(songRepository::existsById)
                .peek(songRepository::deleteById)
                .toList();
        return new DeleteResponseDto(deletedIds);
    }

    private List<Integer> parseCSV(String csv) {
        return Optional.ofNullable(csv)
                .filter(str -> !str.isBlank())
                .stream()
                .flatMap(str -> Stream.of(str.split(",")))
                .peek(songValidator::validateId)
                .map(Integer::parseInt)
                .toList();
    }
}
