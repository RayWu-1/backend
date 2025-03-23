package backend.service;

import backend.entity.ContentEntity;
import backend.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserEntity login(String email, String password);

    Long getNumberOfCreators();

    Page<ContentEntity> getAllByUser(Pageable pageable, String username);
}
