package vn.ohana.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.PostMedia;
import vn.ohana.entities.Role;
import vn.ohana.entities.User;
import vn.ohana.entities.UserStatus;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.post.PostMediaService;
import vn.ohana.user.dto.*;
import vn.rananu.shared.exceptions.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserFilterRepository userFilterRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PostMediaService postMediaService;

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public boolean existsByPhoneOrEmail(String phoneOrEmail) {
        return userRepository.existsByPhoneOrEmail(phoneOrEmail, phoneOrEmail);
    }

    @Override
    public UserResult signUp(SignUpParam signUpParam) {
//        check email tồn tại hay chưa
//        Lưu user
//        set các trường mặc định
//        chuyển DTO và trả về
        boolean exits = existsByPhoneOrEmail(signUpParam.getPhoneOrEmail());
        if (exits) {
            throw new RuntimeException("tài khoản đã tồn tại");
        } else {
            User user = new User();
            if (signUpParam.getPhoneOrEmail().contains("@")) {
                user.setEmail(signUpParam.getPhoneOrEmail());
            } else {
                user.setPhone(signUpParam.getPhoneOrEmail());
            }
            user.setFullName(signUpParam.getFullName());
            user.setPassword(signUpParam.getPassword());
            user.setStatus(UserStatus.ACTIVATED);
            user.setRole(Role.USER);
            userRepository.save(user);
            return userMapper.toUserDTO(user);
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category.exception.notFound"));
    }


    public Page<UserResult> getAll(Pageable pageable) {
        Page<User> page = findAll(pageable);
        return userMapper.toDtoPage(page);
    }

    @Override
    @Transactional
    public UserResult update(UserUpdateParam updateParam) {
        User user = findById(updateParam.getId());
        userMapper.transferFields(updateParam, user);
        return userMapper.toDTO(user);
    }

    @Override
    public Page<UserResult> filter(UserFilterParam filter, Pageable pageable) {
        Page<User> page = userFilterRepository.findAllByFilters(filter, pageable);
        return userMapper.toDtoPage(page);
    }

    @Override
    @Transactional
    public Map<String, List<Long>> modifyStatusByIds(Set<Long> ids, String statusRaw) {
        UserStatus status = UserStatus.parseUserStatus(statusRaw);
        Map<String, List<Long>> result = new HashMap<>();
        List<Long> successIds = new ArrayList<>();
        List<Long> failIds = new ArrayList<>();
        Iterable<User> entities = userRepository.findAllById(ids);
        entities.forEach(entity -> {
            entity.setStatus(status);
            successIds.add(entity.getId());
        });
        result.put("succeed",successIds );

        List<Long> entityIds = StreamSupport.stream(entities.spliterator(), false).map(User::getId).collect(Collectors.toList());
        ids.forEach(id -> {
            if (!entityIds.contains(id)) {
                failIds.add((id));
            }
        });
        result.put("failed",failIds);
        return result;
    }

    @Override
    @Transactional
    public void modifyStatusById(Long id, String statusRaw) {
        UserStatus status = UserStatus.parseUserStatus(statusRaw);
        findById(id).setStatus(status);
    }

    @Override
    public UserResult getById(Long id) {
        return userMapper.toDTO(findById(id));
    }

    private Page<UserResult> toDtoPage(Page<User> page) {
        return page.map(entity -> userMapper.toDTO(entity));
    }


    @Override
    public LoginResult findByEmail(String email) {
        User loginResult = userRepository.findByEmail(email);
        if (loginResult != null) {
            return userMapper.toLoginResultDTO(loginResult);
        }
        return null;
    }

    @Override
    public LoginResult saveGoogleEmail(GooglePojo googlePojo) {
        User userCheck = userRepository.findByEmail(googlePojo.getEmail());
        if (userCheck != null) {
            return userMapper.toLoginResultDTO(userCheck);
        } else {
            User user = userMapper.toGooglePojo(googlePojo);
            user.setStatus(UserStatus.ACTIVATED);
            user.setRole(Role.USER);
            user.setPassword(googlePojo.getPassword());
            PostMedia postMedia = new PostMedia();
            postMedia.setFileUrl(googlePojo.getPicture());
            postMediaService.save(postMedia);
            user.setThumbnailId(postMedia.getId());
            User userResult = userRepository.save(user);
            return userMapper.toLoginResultDTO(userResult);
        }
    }
}
