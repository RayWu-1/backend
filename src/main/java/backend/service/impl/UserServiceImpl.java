package backend.service.impl;

import backend.entity.ContentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.entity.UserEntity;
import backend.exception.BusinessException;
import backend.exception.ExceptionEnum;
import backend.repository.UserRepository;
import backend.service.UserService;
import backend.repository.ContentRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContentRepository contentRepository;

    @Override
    public UserEntity login(String email, String password) {
        if (email == null || password == null) {
            throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
        }
        try {
            UserEntity user = userRepository.findByEmail(email);
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                throw new BusinessException(ExceptionEnum.WRONG_PASSWORD);
            }
        } catch (Exception e) {
            throw new BusinessException(ExceptionEnum.USER_NOT_FOUND);
        }
    }
    @Override
    public Long getNumberOfCreators() {
        return userRepository.count();
    }

    @Override
    public Page<ContentEntity> getAllByUser(Pageable pageable, String username) {
        return contentRepository.findByCreatedByUsername(username, pageable);
    }
}