package com.desafio.tools.management.application.web.entities.requests;

import java.util.List;

public class ToolRequest {
    private String title;
    private String link;
    private String description;
    private List<String> tags;

    public ToolRequest(String title, String link, String description, List<String> tags) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.tags = tags;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
