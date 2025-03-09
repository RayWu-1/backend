package backend.controller;

import backend.dto.ResponseDto;
import backend.entity.UserEntity;
import backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseDto login(@RequestParam("username") String username, @RequestParam("password") String password) {
        UserEntity user = userService.login(username, password);
        return new ResponseDto(0, "success", user);
    }
}
