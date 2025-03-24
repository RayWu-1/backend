package backend.controller;

import backend.dto.ResponseDto;
import backend.entity.DataEntity;
import backend.entity.UserEntity;
import backend.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/datas/visits")
public class DataController {

    @Autowired
    private DataService dataService;

    @PostMapping("/record")
    public ResponseDto<Void> recordVisit() {
        dataService.record(LocalDate.now());
        return ResponseDto.success();
    }

    @GetMapping("/get_today")
    public ResponseDto<DataEntity> getTodayVisit() {
        return ResponseDto.success(dataService.getTodayVisit());
    }

}