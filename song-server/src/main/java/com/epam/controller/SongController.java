package com.epam.controller;

import com.epam.dto.SongDto;
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

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> uploadSong(@RequestBody SongDto song) {
        Integer id = songService.addSong(song);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSong(@PathVariable(name = "id") Integer id) {
        return songService
                .getSongById(id)
                .map(song -> new ResponseEntity<>(song, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSong(@RequestParam(value = "id") String ids) {
        List<Integer> deletedSongs = songService.deleteSongs(ids);
        return new ResponseEntity<>(deletedSongs, HttpStatus.OK);
    }
}
