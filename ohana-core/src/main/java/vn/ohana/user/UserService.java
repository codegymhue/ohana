package vn.ohana.user;

import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.UserStatus;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.user.dto.*;

import java.util.List;


public interface UserService {

    void active(Long userId);

    UserResult getById(Long id);

    UserResult signUp(SignUpParam signUpParam);

    @Transactional
    UserResult update(UpdateUserParam param);

    void deleteById(Long id);

    boolean existsByPhoneOrEmail(String phoneOrEmail);

    LoginResult login(String phoneOrEmail, String phoneOrEmail1);

    boolean checkAdmin(LoginParam loginParam);

    List<UserResult> findAllByStatus(UserStatus activated);

    LoginResult findByEmail(String email);

    LoginResult saveGoogleEmail(GooglePojo googlePojo);
}
