package backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.AudioDto;
import backend.dto.ContentDto;
import backend.dto.ImageDto;
import backend.dto.ResponseDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping("content")
public class ContentController {

    @Autowired
    private ContentService contentService;

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
                        audioEntity == null ? null : new AudioDto(audioEntity.getDuration(), audioEntity.getFile().getPath()),
                        videoEntity == null ? null : new VideoDto(videoEntity.getDuration(), videoEntity.getFile().getPath()),
                        contentEntity.getStartTime(),
                        contentEntity.getEndTime());
            }));
        } catch (NumberFormatException e) {
            log.warn("Pagination parameters are not numbers: page={}, size={}", page, size);
            throw new BusinessException(ExceptionEnum.ILLEGAL_PARAMETERS);
        }
    }

}