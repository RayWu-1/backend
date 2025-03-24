package backend.service;

import backend.entity.ContentEntity;
import java.util.List;

import backend.dto.LoginDto;
import backend.entity.GuestEntity;
import backend.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Long getNumberOfCreators();

    Page<ContentEntity> getAllByUser(Pageable pageable, String username);

    String login(LoginDto login);

    List<GuestEntity> userDetails();
}
