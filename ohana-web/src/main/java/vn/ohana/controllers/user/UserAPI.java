package vn.ohana.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.entities.User;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.UserUpdateParam;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
    @Autowired
    UserService userService;

    @PatchMapping("/{id}")
    public ResponseEntity<?> doUpdate(@PathVariable Long id,@RequestBody UserUpdateParam creationParam) {
        return null;
    }
}
