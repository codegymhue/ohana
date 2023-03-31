package vn.ohana.user;

import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.UserStatus;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.user.dto.*;

import java.util.List;


public interface UserService {
    Page<UserResult> getAll(Pageable pageable);

    UserResult update(UserUpdateParam updateParam);

    Page<UserResult> filter(UserFilterParam filter, Pageable pageable);
}
