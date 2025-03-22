package backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseDto<TagEntity> renameTag(@PathVariable Long id, String newName) {
        // path variable is never null
        if (newName == null) {
            throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
        }
        return ResponseDto.success(tagService.renameTag(id, newName));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseDto.success();
    }

}
