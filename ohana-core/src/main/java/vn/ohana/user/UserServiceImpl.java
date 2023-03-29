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

}
