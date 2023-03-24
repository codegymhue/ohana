package vn.ohana.user;

import org.springframework.transaction.annotation.Transactional;
import vn.ohana.User;
import vn.ohana.UserStatus;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.user.dto.*;

import java.util.List;
import java.util.Optional;


public interface UserService {

    void active(Long userId);

    UserResult getById(Long id);

    UserResult signUp(SignUpParam signUpParam);

    @Transactional
    UserResult update(UpdateUserParam param);

    void deleteById(Long id);

}
