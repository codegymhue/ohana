package vn.ohana.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.User;
import vn.ohana.post.PostMediaService;
import vn.ohana.user.dto.UserFilterParam;
import vn.ohana.user.dto.UserResult;
import vn.ohana.user.dto.UserUpdateParam;
import vn.rananu.shared.exceptions.NotFoundException;

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

    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category.exception.notFound"));
    }


    public Page<UserResult> getAll(Pageable pageable) {
        Page<User> page = findAll(pageable);
        return page.map(entity->userMapper.toDTO(entity));
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

    @Override
    public Page<UserResult> filter(UserFilterParam filter, Pageable pageable) {
        Page<User> page = userFilterRepository.findAllByFilters(filter,pageable);
        return toDtoPage(page);
    }

    private Page<UserResult> toDtoPage(Page<User> page) {

        return  page.map(entity -> userMapper.toDTO(entity));
    }

}
