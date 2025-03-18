package com.epam.controller;

import com.epam.dto.DeleteResponseDto;
import com.epam.dto.UploadResponseDto;
import com.epam.service.ResourceService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
@Validated
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    public ResponseEntity<byte[]> download(@PathVariable("id") @Positive Integer id) {
        byte[] response = resourceService.getFileById(id).getPayload();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping(consumes = "audio/mpeg")
    public ResponseEntity<UploadResponseDto> upload(@RequestBody byte[] file) {
        UploadResponseDto response = resourceService.addFile(file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<DeleteResponseDto> delete(@RequestParam("id") String ids) {
        DeleteResponseDto response = resourceService.deleteFiles(ids);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
