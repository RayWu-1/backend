package backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByStoredFilename(String storedFilename);
}