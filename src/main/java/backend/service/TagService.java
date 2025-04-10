package backend.service;

import java.util.List;

import backend.entity.TagEntity;

public interface TagService {
    List<TagEntity> getAllTags();
    TagEntity renameTag(Long tagId, String newName);
    void deleteTag(Long id);
    void mergeTag(Long targetTagId, Long sourceTagId);
}
