package backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.entity.UserEntity;
import backend.exception.BusinessException;
import backend.exception.ExceptionEnum;
import backend.repository.UserRepository;
import backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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

    public long getNumberOfCreators() {
        return userRepository.count();
    }
}