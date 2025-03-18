package backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.entity.ContentEntity;
import backend.entity.TagEntity;
import backend.repository.ContentRepository;
import backend.repository.TagRepository;
import backend.service.ContentService;
import backend.exception.BusinessException;
import backend.exception.ExceptionEnum;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Page<ContentEntity> getContentList(Pageable pageable) {
        return contentRepository.findAll(pageable);
    }

    @Override
    public void deleteContent(Long id) {
        ContentEntity content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ExceptionEnum.CONTENT_NOT_FOUND.getMessage()));
        content.setStatus(backend.entity.enums.ContentStatus.DRAFT);
        contentRepository.save(content);
    }

    @Override
    public void addTagToContent(Long contentId, Long tagId) {
        ContentEntity content = contentRepository.findById(contentId)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.CONTENT_NOT_FOUND));
        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.TAG_NOT_FOUND));

        content.getTags().add(tag);
        contentRepository.save(content);
    }

    @Override
    public void deleteTagFromContent(Long contentId, Long tagId) {
        ContentEntity content = contentRepository.findById(contentId)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.CONTENT_NOT_FOUND));
        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.TAG_NOT_FOUND));

        if (!content.getTags().contains(tag)) {
            throw new BusinessException(ExceptionEnum.TAG_NOT_FOUND);
        }

        content.getTags().remove(tag);
        contentRepository.save(content);
    }

}
