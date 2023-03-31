package vn.ohana.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.filter.dto.FilterParam;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.UserFilterParam;
import vn.ohana.user.dto.UserUpdateParam;


@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserAPI {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers (Pageable pageable){
        return new ResponseEntity<>(userService.getAll(pageable),HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter( @RequestBody UserFilterParam filter, Pageable pageable){
        return new ResponseEntity<>(userService.filter(filter, pageable), HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> doUpdate(@PathVariable Long id,@RequestBody UserUpdateParam updateParam) {

        return new ResponseEntity<>(userService.update(updateParam),HttpStatus.OK);
    }
}
