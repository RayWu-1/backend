package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}