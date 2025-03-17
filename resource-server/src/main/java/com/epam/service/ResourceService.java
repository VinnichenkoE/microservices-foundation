package com.epam.service;

import com.epam.db.entity.ResourceEntity;

import java.util.List;
import java.util.Optional;

public interface ResourceService {

    Integer addFile(byte[] payload);

    Optional<ResourceEntity> getFileById(Integer id);

    List<Integer> deleteFiles(String ids);
}
