package vn.ohana.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.User;
import vn.ohana.entities.UserStatus;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.post.PostMediaService;
import vn.ohana.user.dto.*;
import vn.rananu.shared.exceptions.NotFoundException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PostMediaService postMediaService;


    @Transactional(readOnly = true)
    User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category.exception.notFound"));
    }

    @Override
    public void active(Long userId) {

    }

    @Override
    public UserResult getById(Long id) {
        return userMapper.toDTO(findById(id));
    }

    @Override
    public UserResult signUp(SignUpParam signUpParam) {
        return null;
    }

    @Override
    @Transactional
    public UserResult update(UpdateUserParam param) {
        User category;
        category = findById(param.getId());
        userMapper.transferFields(param, category);
        return userMapper.toDTO(category);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByPhoneOrEmail(String phoneOrEmail) {
        return false;
    }

    @Override
    public LoginResult login(String phoneOrEmail, String phoneOrEmail1) {
        return null;
    }

    @Override
    public boolean checkAdmin(LoginParam loginParam) {
        return false;
    }

    @Override
    public List<UserResult> findAllByStatus(UserStatus activated) {
        return null;
    }

    @Override
    public LoginResult findByEmail(String email) {
        return null;
    }

    @Override
    public LoginResult saveGoogleEmail(GooglePojo googlePojo) {
        return null;
    }

}
