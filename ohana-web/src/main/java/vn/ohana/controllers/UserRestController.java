package vn.ohana.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.entities.UserStatus;
import vn.ohana.user.dto.UserResult;
import vn.ohana.user.UserService;

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
