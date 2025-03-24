package backend.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.entity.DataEntity;

public interface DataRepository extends JpaRepository<DataEntity, Integer> {

    DataEntity findByDate(LocalDate date);
}
