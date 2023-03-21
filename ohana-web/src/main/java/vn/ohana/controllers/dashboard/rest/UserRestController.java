package vn.ohana.controllers.dashboard.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.user.dto.UserResult;
import vn.ohana.user.UserService;
import vn.tg.ohana.repository.model.StatusPost;
import vn.tg.ohana.repository.model.User;
import vn.tg.ohana.repository.model.UserStatus;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    @Autowired
    private UserService userService;

    @GetMapping("/active")
    public ResponseEntity<?> getUtility() {
        List<UserResult> user = userService.findAllByStatus(UserStatus.ACTIVATED);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
