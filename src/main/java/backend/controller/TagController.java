package backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.ResponseDto;
import backend.entity.TagEntity;
import backend.service.TagService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public ResponseDto<List<TagEntity>> getAllTags() {
        return ResponseDto.success(tagService.getAllTags());
    }
}
