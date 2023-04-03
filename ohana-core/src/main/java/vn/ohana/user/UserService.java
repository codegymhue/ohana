package vn.ohana.user;

import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.UserStatus;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.user.dto.*;
import vn.rananu.shared.mappers.BaseMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface UserService  {
    Page<UserResult> getAll(Pageable pageable);

    UserResult update(UserUpdateParam updateParam);

    Page<UserResult> filter(UserFilterParam filter, Pageable pageable);

//   void deactivateAllByIds(Long[] ids);

    Map<Long, String> modifyStatusByIds(Set<Long> ids, String status);

    @Transactional
    void modifyStatusById(Long id, String statusRaw);

    UserResult getById(Long id);

    LoginResult saveGoogleEmail(GooglePojo googlePojo);

    UserResult signUp(SignUpParam signUpParam);

    boolean existsByPhoneOrEmail(String phoneOrEmail);

    LoginResult findByEmail(String email);


}
