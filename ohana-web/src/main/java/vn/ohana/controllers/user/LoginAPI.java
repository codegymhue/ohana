package vn.ohana.controllers.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.ohana.entities.Role;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.LoginParam;
import vn.ohana.user.dto.LoginResult;
import vn.ohana.utils.AppUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

@RestController
@RequestMapping("/api/login")
public class LoginAPI {

    @Autowired
    UserService userService;

    @Autowired
    AppUtils appUtils;
    @GetMapping("/existsByPhoneOrEmail")
    public ResponseEntity<?> existsByPhoneOrEmail(String phoneOrEmail) throws IOException {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @GetMapping("/admin")
    public ResponseEntity<?> doLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getValue().equals("admin@gmail.com")) {
                    return new ResponseEntity<>(HttpStatus.ACCEPTED);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    @PostMapping("/admin")
    public ResponseEntity<?> doLogin(@RequestBody LoginParam loginParam, BindingResult bindingResult,HttpServletResponse response) {

        new LoginParam().validate(loginParam, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return  appUtils.mapErrorToResponse(bindingResult);
        }
        LoginResult loginResult = userService.findByEmailAndPasswordMapper(loginParam.getEmail(), loginParam.getPassword());

        if (!loginResult.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Cookie adminCookie = new Cookie("admin", loginParam.getEmail());
        Cookie pwdCookie = new Cookie("pwd", loginParam.getPassword());
        adminCookie.setMaxAge(24 * 60 * 60 * 30);
        pwdCookie.setMaxAge(24 * 60 * 60 * 30);
        response.addCookie(adminCookie);
        response.addCookie(pwdCookie);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, adminCookie.toString() + pwdCookie.toString()).body("Đăng nhập thành công!");
    }

    @GetMapping("/sign-out")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        return null;
    }

}
