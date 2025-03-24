package backend.service;

import java.util.List;

import backend.dto.LoginDto;
import backend.entity.GuestEntity;
import backend.entity.UserEntity;

public interface UserService {

    String login(LoginDto login);
    List<GuestEntity> userDetails();
}
