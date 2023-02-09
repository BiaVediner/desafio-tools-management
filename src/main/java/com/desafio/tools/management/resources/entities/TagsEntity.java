package com.desafio.tools.management.resources.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "tags")
public class TagsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagsId;
    private String title;
    @ManyToMany(mappedBy = "tags")
    private List<ToolsEntity> tools;

    public TagsEntity(String title) {
        this.title = title;
    }

    public TagsEntity() {
    }

    public Long getTagsId() {
        return tagsId;
    }

    public void setTagsId(Long tagsId) {
        this.tagsId = tagsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ToolsEntity> getTools() {
        return tools;
    }

    public void setTools(List<ToolsEntity> tools) {
        this.tools = tools;
    }
}
