package vn.ohana.user;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.UserStatus;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.user.dto.*;

import java.util.List;


public interface UserService {
    List<UserResult> getAll();

    UserResult update(UserUpdateParam updateParam);
}
