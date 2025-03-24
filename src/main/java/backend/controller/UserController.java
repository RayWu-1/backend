package backend.controller;

import backend.dto.ContentDto;
import backend.dto.ResponseDto;
import backend.entity.ContentEntity;
import backend.entity.UserEntity;
import backend.exception.BusinessException;
import backend.exception.ExceptionEnum;
import backend.service.UserService;
import backend.repository.ContentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private ContentRepository contentRepository;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseDto<UserEntity> login(String username, String password) {
        return ResponseDto.success(userService.login(username, password));
    }
    @GetMapping("/count")
    public Long count() {
        return userService.getNumberOfCreators();
    }
    @GetMapping("/contents/by-user")
    public ResponseEntity<Page<ContentEntity>> getAllByUser(
            @RequestParam(value = "username") String username,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<ContentEntity> contents = userService.getAllByUser(pageable, username);
        return ResponseEntity.ok(contents);
    }


}
