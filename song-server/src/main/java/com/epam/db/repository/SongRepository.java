package com.epam.db.repository;

import com.epam.db.entity.SongEntity;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<SongEntity, Integer> {
}
