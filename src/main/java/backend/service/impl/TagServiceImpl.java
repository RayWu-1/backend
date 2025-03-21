package backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.entity.TagEntity;
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

}
