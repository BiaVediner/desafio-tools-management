package com.desafio.tools.management.domain.entities;

import com.desafio.tools.management.domain.entities.enums.ToolsStatus;
import com.desafio.tools.management.resources.entities.TagsEntity;
import com.desafio.tools.management.resources.entities.ToolsEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Tools {
    private Long id;
    private String title;
    private String link;
    private String description;
    private ToolsStatus status;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Tools(ToolsEntity tool) {
        this.id = tool.getId();
        this.title = tool.getTitle();
        this.link = tool.getLink();
        this.description = tool.getDescription();
        this.status = tool.getStatus();
        this.tags = tool.getTags().stream().map(TagsEntity::getTitle).collect(Collectors.toList());
        this.createdAt = tool.getCreatedAt();
        this.updatedAt = tool.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ToolsStatus getStatus() {
        return status;
    }

    public void setStatus(ToolsStatus status) {
        this.status = status;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
