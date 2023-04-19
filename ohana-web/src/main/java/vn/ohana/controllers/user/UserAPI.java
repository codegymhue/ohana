package vn.ohana.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.SignUpParam;
import vn.ohana.user.dto.UserFilterParam;
import vn.ohana.user.dto.UserUpdateParam;

import java.util.Set;


@RestController
@RequestMapping("/api/users")
public class UserAPI {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return new ResponseEntity<>(userService.getAll(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody UserFilterParam filter, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {

        return new ResponseEntity<>(userService.filter(filter, PageRequest.of(page, size)), HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> doUpdate(@PathVariable Long id, @RequestBody UserUpdateParam updateParam) {

        return new ResponseEntity<>(userService.update(updateParam), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/deactivate")
    public ResponseEntity<?> deactivateAll(@RequestBody Set<Long> ids) {

        return new ResponseEntity<>(userService.modifyStatusByIds(ids, "DEACTIVATED"), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/activate")
    public ResponseEntity<?> activateAll(@RequestBody Set<Long> ids) {

        return new ResponseEntity<>(userService.modifyStatusByIds(ids, "ACTIVATED"), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> modifyStatusById(@PathVariable Long id, @RequestParam(name = "status") String status) {
        userService.modifyStatusById(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @PostMapping("/sign-up")
//    public ResponseEntity<?> signUp(SignUpParam signUpParam) {
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
