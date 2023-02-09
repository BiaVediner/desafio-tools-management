package com.desafio.tools.management.resources.repositories;

import com.desafio.tools.management.domain.entities.enums.ToolsStatus;
import com.desafio.tools.management.resources.entities.ToolsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsRepository extends JpaRepository<ToolsEntity, Long> {
    boolean existsByTitle(String title);
    @Query("select t from com.desafio.tools.management.resources.entities.ToolsEntity t WHERE t.status != 'DELETED'")
    List<ToolsEntity> findByStatus(ToolsStatus status);
}
