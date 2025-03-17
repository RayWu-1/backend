package backend.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "datas")
@Data
@NoArgsConstructor
public class DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long visit;

    private LocalDate date;

    public DataEntity(long visit, LocalDate date) {
        this.visit = visit;
        this.date = date;
    }

    public DataEntity(LocalDate date) {
        this.date = date;
    }
}
