package backend.dto;

import java.time.LocalDateTime;
import java.util.Set;

import backend.entity.TagEntity;

public record ContentDto(
        Long id,
        String title,
        String body,
        LocalDateTime createdAt,
        Integer authorId,
        Set<TagEntity> tags,
        LocalDateTime startTime,
        LocalDateTime endTime) {
}