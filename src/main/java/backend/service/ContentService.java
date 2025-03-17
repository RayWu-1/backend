package backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import backend.entity.ContentEntity;

public interface ContentService {
    Page<ContentEntity> getContentList(Pageable pageable);

    void deleteContent(Long id);
}
