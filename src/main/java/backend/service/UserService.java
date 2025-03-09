package backend.service;

import backend.entity.UserEntity;
import java.io.IOException;

public interface UserService {

    UserEntity getUserByUsername(String username) throws IOException;

    UserEntity getUserByEmail(String email) throws IOException;

}
