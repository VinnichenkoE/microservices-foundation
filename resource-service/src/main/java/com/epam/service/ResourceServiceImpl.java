package com.epam.service;

import com.epam.db.entity.ResourceEntity;
import com.epam.db.repository.ResourceRepository;
import com.epam.dto.DeleteResponseDto;
import com.epam.dto.ResourceMetaDataDto;
import com.epam.dto.UploadResponseDto;
import com.epam.exception.ResourceNotFoundException;
import com.epam.parser.ResourceMetaDataParser;
import com.epam.validator.ResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ResourceServiceImpl implements ResourceService {

    private static final int MAX_ID_LENGTH = 200;

    private static final String COMMA = ",";

    private final ResourceRepository resourceRepository;

    private final ResourceMetaDataParser resourceMetaDataParser;

    private final SongIntegrationService songIntegrationService;

    private final ResourceValidator resourceValidator;

    @Autowired
    public ResourceServiceImpl(
            ResourceRepository resourceRepository,
            ResourceMetaDataParser resourceMetaDataParser,
            SongIntegrationService songIntegrationService, ResourceValidator resourceValidator
    ) {
        this.resourceRepository = resourceRepository;
        this.resourceMetaDataParser = resourceMetaDataParser;
        this.songIntegrationService = songIntegrationService;
        this.resourceValidator = resourceValidator;
    }

    @Override
    public UploadResponseDto addFile(byte[] payload) {
        ResourceEntity resource = new ResourceEntity();
        resource.setPayload(payload);
        resource = resourceRepository.save(resource);
        ResourceMetaDataDto resourceMetaDataDto;
        resourceMetaDataDto = resourceMetaDataParser.parse(resource.getId(), payload);
        songIntegrationService.saveResourceMetadata(resourceMetaDataDto);
        return new UploadResponseDto(resource.getId());
    }

    @Override
    public ResourceEntity getFileById(Integer id) {
        resourceValidator.validateId(id);
        return resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageFormat.format("Resource with ID={0} not found", String.valueOf(id))));
    }

    @Override
    @Transactional
    public DeleteResponseDto deleteFiles(String ids) {
        resourceValidator.validateString(ids, MAX_ID_LENGTH);
        List<Integer> deletedIds = parseCSV(ids).stream()
                .filter(resourceRepository::existsById)
                .peek(resourceRepository::deleteById)
                .toList();

        songIntegrationService.deleteResourceMetadata(ids);
        return new DeleteResponseDto(deletedIds);
    }

    private List<Integer> parseCSV(String csv) {
        return Optional.ofNullable(csv)
                .filter(str -> !str.isBlank())
                .stream()
                .flatMap(str -> Stream.of(str.split(COMMA)))
                .peek(resourceValidator::validateId)
                .map(Integer::parseInt)
                .toList();
    }
}
