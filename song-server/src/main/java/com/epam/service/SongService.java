package com.epam.service;

import com.epam.dto.DeleteResponseDto;
import com.epam.dto.SongDto;
import com.epam.dto.UploadResponseDto;

public interface SongService {

    UploadResponseDto addSong(SongDto song);

    SongDto getSongById(Integer id);

    DeleteResponseDto deleteSongs(String ids);
}
