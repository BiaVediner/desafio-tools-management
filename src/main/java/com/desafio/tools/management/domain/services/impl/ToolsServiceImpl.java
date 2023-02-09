package com.desafio.tools.management.domain.services.impl;

import com.desafio.tools.management.application.web.entities.requests.ToolRequest;
import com.desafio.tools.management.domain.entities.Tools;
import com.desafio.tools.management.domain.entities.enums.ToolsStatus;
import com.desafio.tools.management.domain.exceptions.AlreadyExistsException;
import com.desafio.tools.management.domain.exceptions.NotFoundException;
import com.desafio.tools.management.domain.exceptions.TagsLimitExceeded;
import com.desafio.tools.management.domain.services.ToolsService;
import com.desafio.tools.management.resources.entities.TagsEntity;
import com.desafio.tools.management.resources.entities.ToolsEntity;
import com.desafio.tools.management.resources.repositories.TagsRepository;
import com.desafio.tools.management.resources.repositories.ToolsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToolsServiceImpl implements ToolsService {
    @Autowired
    private ToolsRepository toolsRepository;
    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public Tools createTool(ToolRequest createToolRequest) throws AlreadyExistsException, TagsLimitExceeded {
        validateToolTitle(createToolRequest.getTitle());
        validateTags(createToolRequest.getTags());

        ToolsEntity tool = new ToolsEntity(createToolRequest);

        return new Tools(
            toolsRepository.save(
                saveTags(createToolRequest.getTags(), tool)
            )
        );
    }

    @Override
    public Tools getToolById(Long id) throws NotFoundException {
        ToolsEntity foundTool = verifyTool(id);

        return new Tools(
            foundTool
        );
    }

    @Override
    public List<Tools> getTools() {
        return toolsRepository.findByStatus(ToolsStatus.CREATED).stream().map(Tools::new).collect(Collectors.toList());
    }

    @Override
    public void deleteTool(Long id) throws NotFoundException {
        ToolsEntity tool = verifyTool(id);

        toolsRepository.save(new ToolsEntity(
            tool.getId(),
            tool.getTitle(),
            tool.getLink(),
            tool.getDescription(),
            ToolsStatus.DELETED,
            tool.getCreatedAt()
        ));
    }

    @Override
    public Tools updateTool(Long id, ToolRequest updateTool) throws NotFoundException, TagsLimitExceeded {
        validateTags(updateTool.getTags());

        ToolsEntity tool = verifyTool(id);

        ToolsEntity updatedTool = new ToolsEntity(
                id,
                updateTool.getTitle(),
                updateTool.getLink(),
                updateTool.getDescription(),
                ToolsStatus.UPDATED,
                tool.getCreatedAt()
        );

        return new Tools(
            toolsRepository.save(saveTags(updateTool.getTags(), updatedTool))
        );
    }

    private ToolsEntity saveTags(List<String> tags, ToolsEntity tool) {
        for (String createTag: tags) {
            Optional<TagsEntity> foundTag = verifyTag(createTag);
            TagsEntity tag = foundTag.orElseGet(() -> tagsRepository.save(new TagsEntity(createTag)));

            tool.getTags().add(tag);
        }

        return tool;
    }

    private Optional<TagsEntity> verifyTag(String tag) {
        return tagsRepository.findByTitle(tag);
    }

    private ToolsEntity verifyTool(Long id) throws NotFoundException {
        Optional<ToolsEntity> tool = toolsRepository.findById(id);

        if(tool.isEmpty() || tool.get().getStatus() == ToolsStatus.DELETED) {
            throw new NotFoundException("tool does not exist");
        }

        return tool.get();
    }

    private void validateToolTitle(String title) throws AlreadyExistsException {
        if(toolsRepository.existsByTitle(title)) {
            throw new AlreadyExistsException("Tool already exists");
        }
    }

    private void validateTags(List<String> tags) throws TagsLimitExceeded {
        if(tags.size() >= 8) {
            throw new TagsLimitExceeded("Amount of tags is higher than 8");
        }
    }
}
