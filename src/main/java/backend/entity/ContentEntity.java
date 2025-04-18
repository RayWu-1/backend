package backend.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import backend.entity.enums.ContentStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "contents")
@EqualsAndHashCode(exclude = { "images", "audio", "video", "tags" })
public class ContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    @ManyToMany
    @JoinTable(name = "content_tag_rel", joinColumns = @JoinColumn(name = "content_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    Set<TagEntity> tags;

    ContentStatus status;

    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<ImageEntity> images;

    @OneToOne(mappedBy = "content")
    AudioEntity audio;

    @OneToOne(mappedBy = "content")
    VideoEntity video;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
