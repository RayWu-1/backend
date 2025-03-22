package backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.MergeTagDto;
import backend.dto.RenameTagDto;
import backend.dto.ResponseDto;
import backend.entity.TagEntity;
import backend.exception.BusinessException;
import backend.exception.ExceptionEnum;
import backend.service.TagService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("")
    public ResponseDto<List<TagEntity>> getAllTags() {
        return ResponseDto.success(tagService.getAllTags());
    }

    @PutMapping("/{id}/rename")
    public ResponseDto<TagEntity> renameTag(@PathVariable Long id, @RequestBody RenameTagDto request) {
        String newName = request.getNewName();
        if (newName == null) {
            throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
        }
        if (!StringUtils.hasText(newName)) {
            throw new BusinessException(ExceptionEnum.ILLEGAL_PARAMETERS);
        }
        return ResponseDto.success(tagService.renameTag(id, newName));
    }

    @PutMapping("/merge")
    public ResponseDto<Void> mergeTag(@RequestBody MergeTagDto request) {
        Long targetTagId = request.getTargetTagId();
        Long sourceTagId = request.getSourceTagId();

        if (targetTagId == null || sourceTagId == null) {
            throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
        }

        if (targetTagId.equals(sourceTagId)) {
            throw new BusinessException(ExceptionEnum.ILLEGAL_PARAMETERS);
        }

        tagService.mergeTag(targetTagId, sourceTagId);

        return ResponseDto.success();
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseDto.success();
    }

}
