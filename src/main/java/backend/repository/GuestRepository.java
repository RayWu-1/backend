package backend.repository;

import backend.entity.GuestEntity;
import backend.entity.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<GuestEntity, Integer> {

    List<GuestEntity> findAll();
}
