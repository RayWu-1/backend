package backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import backend.entity.ContentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentRepository extends JpaRepository<ContentEntity, Long> {
    List<ContentEntity> findByTags_Id(Long id);

    @Query("SELECT c FROM ContentEntity c JOIN c.createdBy u WHERE u.email = :username")
    Page<ContentEntity> findByCreatedByUsername(@Param("username") String username, Pageable pageable);
}