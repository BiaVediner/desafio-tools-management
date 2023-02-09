package com.desafio.tools.management.resources.repositories;

import com.desafio.tools.management.resources.entities.TagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagsRepository extends JpaRepository<TagsEntity, Long> {
    Optional<TagsEntity> findByTitle(String title);
}
