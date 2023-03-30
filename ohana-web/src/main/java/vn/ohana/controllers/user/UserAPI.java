package vn.ohana.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.entities.User;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.UserResult;
import vn.ohana.user.dto.UserUpdateParam;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserAPI {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers (){


        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> doUpdate(@PathVariable Long id,@RequestBody UserUpdateParam updateParam) {

        return new ResponseEntity<>(userService.update(updateParam),HttpStatus.OK);
    }
}
