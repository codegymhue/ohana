package vn.ohana.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<?> getAllUsers (@RequestParam(name = "page") int  page, @RequestParam(name = "size") int size){
        return new ResponseEntity<>(userService.getAll(PageRequest.of(page,size)),HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody UserFilterParam filter, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return new ResponseEntity<>(userService.filter(filter, PageRequest.of(page,size)), HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> doUpdate(@PathVariable Long id,@RequestBody UserUpdateParam updateParam) {

        return new ResponseEntity<>(userService.update(updateParam),HttpStatus.OK);
    }

    @PatchMapping("/deactivate")
    public ResponseEntity<?> deactivateAll(@RequestBody Long[] ids) {
        userService.deactivateAllByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping("/activate")
    public ResponseEntity<?> activateAll(@RequestBody Long[] ids) {
        userService.activateAllByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
