package backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.entity.TagEntity;
import backend.exception.BusinessException;
import backend.exception.ExceptionEnum;
import backend.repository.TagRepository;
import backend.service.TagService;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<TagEntity> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public TagEntity renameTag(Long tagId, String newName) {
        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.TAG_NOT_FOUND));
        tag.setName(newName);
        return tagRepository.save(tag);
    }

}
