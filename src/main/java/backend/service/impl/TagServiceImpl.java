package backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.entity.ContentEntity;
import backend.entity.TagEntity;
import backend.exception.BusinessException;
import backend.exception.ExceptionEnum;
import backend.repository.ContentRepository;
import backend.repository.TagRepository;
import backend.service.TagService;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Override
    public List<TagEntity> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public TagEntity renameTag(Long tagId, String newName) {
        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.TAG_NOT_FOUND));

        if (tagRepository.existsByName(newName)) {
            throw new BusinessException(ExceptionEnum.TAG_NAME_ALREADY_EXISTS);
        }

        tag.setName(newName);
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void mergeTag(Long targetTagId, Long sourceTagId) {
        TagEntity targetTag = tagRepository.findById(targetTagId)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.TAG_NOT_FOUND));
        TagEntity sourceTag = tagRepository.findById(sourceTagId)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.TAG_NOT_FOUND));

        // Update all content references from sourceTag to targetTag
        List<ContentEntity> contents = contentRepository.findByTags_Id(sourceTagId);
        for (ContentEntity content : contents) {
            content.getTags().remove(sourceTag);
            content.getTags().add(targetTag);
            contentRepository.save(content);
        }

        // Delete the source tag
        tagRepository.delete(sourceTag);
    }

    @Override
    public void deleteTag(Long id) {
        TagEntity tag = tagRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.TAG_NOT_FOUND));

        // Check if the tag is associated with any content
        List<ContentEntity> contents = contentRepository.findByTags_Id(id);
        if (!contents.isEmpty()) {
            throw new BusinessException(ExceptionEnum.TAG_IN_USE);
        }

        tagRepository.delete(tag);
    }

}
