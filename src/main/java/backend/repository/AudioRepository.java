package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.entity.AudioEntity;

public interface AudioRepository extends JpaRepository<AudioEntity, Long> {
}