package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.entity.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
}