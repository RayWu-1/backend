package backend.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.entity.DataEntity;
import backend.repository.DataRepository;
import backend.service.DataService;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataRepository dataRepository;

    @Override
    public DataEntity record(LocalDate date) {

        DataEntity todayVisit = dataRepository.findByDate(date);

        if (todayVisit != null) {
            todayVisit.setVisit(todayVisit.getVisit() + 1);
            dataRepository.save(todayVisit);
            return todayVisit;
        } else {
            DataEntity dailyVisit = new DataEntity(1, date);
            dataRepository.save(dailyVisit);
        }

        return todayVisit;
    }

    @Override
    public DataEntity getTodayVisit() {
        LocalDate today = LocalDate.now();
        return dataRepository.findByDate(today);
    }
}
