package vn.ohana.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
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

    public  List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category.exception.notFound"));
    }

    @Override
    public List<UserResult> getAll() {
        return userMapper.toDTOList(findAll());
    }

    @Override
    @Transactional
    public UserResult update(UserUpdateParam updateParam) {
        try {
            User user  = findById(updateParam.getId());
            userMapper.transferFieldsSkipNull(updateParam,user);
            return userMapper.toDTO(user);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


}
