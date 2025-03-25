package com.epam.controller;

import com.epam.dto.DeleteResponseDto;
import com.epam.dto.SongDto;
import com.epam.dto.UploadResponseDto;
import com.epam.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UploadResponseDto> uploadSong(@RequestBody SongDto song) {
        UploadResponseDto response = songService.addSong(song);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDto> getSong(@PathVariable(name = "id") Integer id) {
        SongDto response = songService.getSongById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<DeleteResponseDto> deleteSong(@RequestParam(value = "id") String ids) {
        DeleteResponseDto response = songService.deleteSongs(ids);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
