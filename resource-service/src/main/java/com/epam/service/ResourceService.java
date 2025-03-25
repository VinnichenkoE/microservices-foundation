package com.epam.service;

import com.epam.db.entity.ResourceEntity;
import com.epam.dto.DeleteResponseDto;
import com.epam.dto.UploadResponseDto;

public interface ResourceService {

    UploadResponseDto addFile(byte[] payload);

    ResourceEntity getFileById(Integer id);

    DeleteResponseDto deleteFiles(String ids);
}
