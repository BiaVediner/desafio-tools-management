package com.desafio.tools.management.resources.repositories;

import com.desafio.tools.management.resources.entities.TagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<TagsEntity, Long> {
    Optional<TagsEntity> findByTitle(String title);
}
