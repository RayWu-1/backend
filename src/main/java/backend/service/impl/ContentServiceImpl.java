package backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.entity.ContentEntity;
import backend.repository.ContentRepository;
import backend.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    ContentRepository contentRepository;

    @Override
    public Page<ContentEntity> getContentList(Pageable pageable) {
        return contentRepository.findAll(pageable);
    }

}
