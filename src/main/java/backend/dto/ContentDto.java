package backend.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import backend.entity.TagEntity;

public record ContentDto(
        Long id,
        String title,
        String body,
        LocalDateTime createdAt,
        Integer authorId,
        Set<TagEntity> tags,
        List<ImageDto> images,
        AudioDto audio,
        VideoDto video,
        LocalDateTime startTime,
        LocalDateTime endTime) {
}