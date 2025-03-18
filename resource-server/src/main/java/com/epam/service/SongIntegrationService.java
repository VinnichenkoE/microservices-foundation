package com.epam.service;

import com.epam.dto.ResourceMetaDataDto;

public interface SongIntegrationService {

    void saveResourceMetadata(ResourceMetaDataDto resourceMetaData);

    void deleteResourceMetadata(String ids);
}
