package backend.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import backend.entity.AudioEntity;
import backend.entity.ContentEntity;
import backend.entity.TagEntity;
import backend.entity.VideoEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public static ContentDto fromEntity(ContentEntity contentEntity) {
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
    }
}