package backend.service;

import backend.entity.UserEntity;

public interface UserService {

    UserEntity login(String email, String password);

    Long getNumberOfCreators();
}
