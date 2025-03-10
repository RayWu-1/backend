package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.entity.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}