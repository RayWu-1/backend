package backend.service.impl;

import backend.entity.ContentEntity;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.dto.LoginDto;
import backend.entity.UserEntity;
import backend.entity.GuestEntity;
import backend.exception.BusinessException;
import backend.exception.ExceptionEnum;
import backend.repository.GuestRepository;
import backend.repository.UserRepository;
import backend.service.JwtService;
import backend.service.UserService;
import backend.repository.ContentRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContentRepository contentRepository;
    private GuestRepository guestRepository;
    @Autowired
    private JwtService jwtService;

    @Override
    public String login(LoginDto login) {
        String email = login.getEmail();
        String password = login.getPassword();
        // System.out.println(email + password);
        if (email == null || password == null) {
            throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
        }
        try {
            UserEntity user = userRepository.findByEmail(email);
            if (user.getPassword().equals(password)) {
                return jwtService.setToken(user.getId());
            } else {
                throw new BusinessException(ExceptionEnum.WRONG_PASSWORD);
            }
        } catch (Exception e) {
            throw new BusinessException(ExceptionEnum.USER_NOT_FOUND);
        }

    }

    @Override
    public List<GuestEntity> userDetails() {
        List<GuestEntity> res = guestRepository.findAll();
        return res;
    }

    @Override
    public Page<ContentEntity> getAllByUser(Pageable pageable, String username) {
        try {
            return contentRepository.findByCreatedByUsername(username, pageable);
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException(ExceptionEnum.CONTENT_NOT_FOUND);
        } catch (Exception e) {
            throw new BusinessException(ExceptionEnum.USER_NOT_FOUND);
        }

    }

    @Override
    public Long getNumberOfCreators() {
        return userRepository.count();
    }
}