package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.entity.ContentEntity;

public interface ContentRepository extends JpaRepository<ContentEntity, Long> {
}