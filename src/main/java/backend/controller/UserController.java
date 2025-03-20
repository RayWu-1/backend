package backend.controller;

import backend.dto.ResponseDto;
import backend.entity.UserEntity;
import backend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

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
}
