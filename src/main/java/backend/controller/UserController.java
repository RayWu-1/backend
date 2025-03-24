package backend.controller;

import backend.dto.LoginDto;
import backend.dto.ResponseDto;
import backend.entity.UserEntity;
import backend.entity.GuestEntity;
import backend.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
// import org.apache.catalina.connector.Request;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

}
