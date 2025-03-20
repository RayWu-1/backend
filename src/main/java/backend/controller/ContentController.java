package backend.controller;

import backend.entity.enums.ContentStatus;
import backend.repository.ContentRepository;
import org.springframework.web.bind.annotation.*;

import backend.dto.AddTagToContentDto;
import backend.dto.AudioDto;
import backend.dto.ContentDto;
import backend.dto.ImageDto;
import backend.dto.ResponseDto;
import backend.dto.UpdateTagsDto;
import backend.dto.VideoDto;
import backend.entity.AudioEntity;
import backend.entity.ContentEntity;
import backend.entity.VideoEntity;
import backend.exception.BusinessException;
import backend.exception.ExceptionEnum;
import backend.service.ContentService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
@RestController
@RequestMapping("content")
public class ContentController {

    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentRepository contentRepository;

    @GetMapping("/list")
    public ResponseDto<Page<ContentDto>> getContentList(@RequestParam String page, @RequestParam String size) {
        try {
            int pageNumber = Integer.parseInt(page);
            int pageSize = Integer.parseInt(size);

            if (pageNumber < 0 || pageSize <= 0) {
                log.warn("Invalid pagination parameters: page={}, size={}", page, size);
                throw new BusinessException(ExceptionEnum.ILLEGAL_PARAMETERS);
            }

            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            log.info("Pagination request: page number = {}, page size = {}, offset = {}", pageable.getPageNumber(),
                    pageable.getPageSize(), pageable.getOffset());

            Page<ContentEntity> results = contentService.getContentList(pageable);

            return ResponseDto.success(results.map(contentEntity -> {
                List<ImageDto> images = contentEntity.getImages().stream()
                        .map(image -> new ImageDto(image.getWidth(), image.getHeight(),
                                image.getFile().getPath()))
                        .collect(Collectors.toList());
                AudioEntity audioEntity = contentEntity.getAudio();
                VideoEntity videoEntity = contentEntity.getVideo();
                return new ContentDto(
                        contentEntity.getId(),
                        contentEntity.getTitle(),
                        contentEntity.getBody(),
                        contentEntity.getCreatedAt(),
                        contentEntity.getCreatedBy().getId(),
                        contentEntity.getTags(),
                        images,
                        audioEntity == null ? null
                                : new AudioDto(audioEntity.getDuration(), audioEntity.getFile().getPath()),
                        videoEntity == null ? null
                                : new VideoDto(videoEntity.getDuration(), videoEntity.getFile().getPath()),
                        contentEntity.getStartTime(),
                        contentEntity.getEndTime());
            }));
        } catch (NumberFormatException e) {
            log.warn("Pagination parameters are not numbers: page={}, size={}", page, size);
            throw new BusinessException(ExceptionEnum.ILLEGAL_PARAMETERS);
        }
    }

    @PatchMapping("/{id}/delete")
    public ResponseDto<Void> deleteContent(@PathVariable Long id) {
        ContentEntity content = contentRepository.findById(id).orElseThrow(() -> new RuntimeException(ExceptionEnum.CONTENT_NOT_FOUND.getMessage()));
        if(content.getStatus() == ContentStatus.PUBLISHED) {
            contentService.deleteContent(id);
            return ResponseDto.success();
        }
        throw new RuntimeException(ExceptionEnum.IS_NOT_PUBLISHED.getMessage());
    }

    @PostMapping("/{contentId}/tags")
    public ResponseDto<Void> addTagToContent(@PathVariable Long contentId, @RequestBody AddTagToContentDto request) {
        Long tagId = request.getTagId();

        if (contentId == null || tagId == null) {
            throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
        }

        contentService.addTagToContent(contentId, tagId);

        return ResponseDto.success();
    }

    @DeleteMapping("/{contentId}/tags/{tagId}")
    public ResponseDto<Void> deleteTagFromContent(@PathVariable Long contentId, @PathVariable Long tagId) {
        if (contentId == null || tagId == null) {
            throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
        }

        contentService.deleteTagFromContent(contentId, tagId);

        return ResponseDto.success();
    }

    @PutMapping("/{contentId}/tags")
    public ResponseDto<Void> updateTagsForContent(@PathVariable Long contentId, @RequestBody UpdateTagsDto request) {
        contentService.updateTagsForContent(contentId, request.getTagIds());
        return ResponseDto.success();
    }

}