package backend.controller;

import backend.dto.LoginDto;
import backend.dto.ContentDto;
import backend.dto.ResponseDto;
import backend.entity.ContentEntity;
import backend.entity.UserEntity;
import backend.entity.GuestEntity;
import backend.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
// import org.apache.catalina.connector.Request;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import backend.exception.BusinessException;
import backend.exception.ExceptionEnum;
import backend.service.UserService;
import backend.repository.ContentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private ContentRepository contentRepository;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody LoginDto login) {
        return ResponseDto.success(userService.login(login));
    }
    @GetMapping("/count")
    public Long count() {
        return userService.getNumberOfCreators();
    }

    @GetMapping("/getusers")
    public ResponseDto<List<GuestEntity>> getusers() {
        return ResponseDto.success(userService.userDetails());
    }

    @GetMapping("/contents/by-user")
    public ResponseEntity<Page<ContentEntity>> getAllByUser(
            @RequestParam(value = "username") String username,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<ContentEntity> contents = userService.getAllByUser(pageable, username);
        return ResponseEntity.ok(contents);
    }


}
