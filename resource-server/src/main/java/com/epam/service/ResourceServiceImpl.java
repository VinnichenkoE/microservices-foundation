package com.epam.service;

import com.epam.db.entity.ResourceEntity;
import com.epam.db.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Integer addFile(byte[] payload) {
        ResourceEntity resource = new ResourceEntity();
        resource.setPayload(payload);
        resource = resourceRepository.save(resource);
        return resource.getId();
    }

    @Override
    public Optional<ResourceEntity> getFileById(Integer id) {
        return resourceRepository.findById(id);
    }

    @Override
    public List<Integer> deleteFiles(String ids) {
        return parseCSV(ids).stream()
                .filter(resourceRepository::existsById)
                .peek(resourceRepository::deleteById)
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
