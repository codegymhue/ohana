package vn.ohana.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.User;
import vn.ohana.post.PostMediaService;
import vn.ohana.user.dto.SignUpParam;
import vn.ohana.user.dto.UpdateUserParam;
import vn.ohana.user.dto.UserResult;
import vn.rananu.exceptions.NotFoundException;

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

}
