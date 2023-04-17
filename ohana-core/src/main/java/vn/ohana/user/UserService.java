package vn.ohana.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.User;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.user.dto.*;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface UserService  {
    Page<UserResult> getAll(Pageable pageable);

    UserResult update(UserUpdateParam updateParam);

    Page<UserResult> filter(UserFilterParam filter, Pageable pageable);

//   void deactivateAllByIds(Long[] ids);

    Map<String, List<Long>> modifyStatusByIds(Set<Long> ids, String status);

    @Transactional
    void modifyStatusById(Long id, String statusRaw);

    UserResult getById(Long id);

    UserResult signUpByGoogle(GooglePojo googlePojo);

    UserResult signUp(SignUpParam signUpParam);

    boolean existsByPhoneOrEmail(String phoneOrEmail);



    LoginResult findByEmailAndPassword(String email, String password);

    LoginResult findByEmailAndPasswordMapper(String email, String password);

    UserResult findByEmailAndPasswordUserResult(String email, String password);
    boolean existsByPhoneOrEmail(String phone, String email);

    boolean existsByEmail(String email);

    UserResult findByEmail(String email);

    String findUserPasswordById(Long id);

    UserResult savePassword(UserUpdateParam userUpdateParam);

    User findById(Long idUser);

    UserResult save(UserUpdateParam userUpdateParam);



}
