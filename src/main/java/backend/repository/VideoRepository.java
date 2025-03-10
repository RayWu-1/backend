package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.entity.VideoEntity;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
}