package vn.ohana.controllers.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.LoginParam;
import vn.ohana.user.dto.LoginResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/login")
public class LoginAPI {

    @Autowired
    UserService userService;

    @GetMapping("/existsByPhoneOrEmail")
    public ResponseEntity<?> existsByPhoneOrEmail(String phoneOrEmail) throws IOException {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> doLogin(@RequestBody LoginParam loginParam, @CookieValue(value = "loginUser", defaultValue = "0") String loginUser, HttpServletResponse response, HttpServletRequest request) {
        return null;
    }

    @GetMapping("/sign-out")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        return null;
    }

}
