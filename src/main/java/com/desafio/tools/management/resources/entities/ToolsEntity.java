package com.desafio.tools.management.resources.entities;

import com.desafio.tools.management.application.web.entities.requests.ToolRequest;
import com.desafio.tools.management.domain.entities.enums.ToolsStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tools")
public class ToolsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toolsId;
    private String title;
    private String link;
    private String description;
    @Enumerated(EnumType.STRING)
    private ToolsStatus status;
    @ManyToMany
    @JoinTable(
            name = "tools_tags",
            joinColumns = @JoinColumn(name = "tools_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    private List<TagsEntity> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ToolsEntity(Long id, String title, String link, String description, ToolsStatus status, LocalDateTime createdAt) {
        this.toolsId = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.tags = new ArrayList<>();
        this.updatedAt = LocalDateTime.now();
    }

    public ToolsEntity(ToolRequest createToolRequest) {
        this.title = createToolRequest.getTitle();
        this.link = createToolRequest.getLink();
        this.description = createToolRequest.getDescription();
        this.status = ToolsStatus.CREATED;
        this.tags = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public ToolsEntity() {
    }

    public Long getId() {
        return toolsId;
    }

    public void setId(Long id) {
        this.toolsId = id;
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

    public List<TagsEntity> getTags() {
        return tags;
    }

    public void setTags(List<TagsEntity> tags) {
        this.tags = tags;
    }
}
