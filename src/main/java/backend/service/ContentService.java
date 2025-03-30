package backend.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import backend.entity.ContentEntity;

public interface ContentService {
    Page<ContentEntity> getContentList(Pageable pageable);

    void deleteContent(Long id);

    void addTagToContent(Long contentId, Long tagId);

    void deleteTagFromContent(Long contentId, Long tagId);
    
    void updateTagsForContent(Long contentId, Set<Long> tagIds);

    Page<ContentEntity> search(String query, Pageable pageable);
}
