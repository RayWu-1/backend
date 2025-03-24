package backend.service;

import java.time.LocalDate;

import backend.entity.DataEntity;

public interface DataService {
    DataEntity record(LocalDate date);

    DataEntity getTodayVisit();
}
