package vn.ohana.controllers.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.ohana.entities.Role;
import vn.ohana.jwt.JwtResponse;
import vn.ohana.jwt.JwtService;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.LoginParam;
import vn.ohana.user.dto.LoginResult;
import vn.ohana.user.dto.UserPrinciple;
import vn.ohana.utils.AppUtils;
import vn.rananu.shared.exceptions.ValidationException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@RestController
@RequestMapping("/api/auth/")
public class LoginAPI {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    AppUtils appUtils;
    @GetMapping("/existsByPhoneOrEmail")
    public ResponseEntity<?> existsByPhoneOrEmail(String phoneOrEmail) throws IOException {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @PostMapping("/sign-in")
    public ResponseEntity<?> doLogin(@RequestBody LoginParam loginParam, BindingResult bindingResult) {
        new LoginParam().validate(loginParam, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return  appUtils.mapErrorToResponse(bindingResult);
        }
        String username = loginParam.getEmail();
        UserPrinciple userDetails = userService.findUserPrincipleByEmail(username);

        if (!userDetails.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String password = loginParam.getPassword();

        String jwt = jwtService.generateToken(userDetails);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(authentication);
        } catch (AuthenticationException e) {
            throw new ValidationException("login.exception.emailOrPwd");
        }
        JwtResponse jwtResponse = new JwtResponse (jwt,userDetails.getId(),username,authenticate.getAuthorities());

        ResponseCookie springCookie = ResponseCookie.from("jwtToken", jwt)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(60 * 1000)
                .domain("localhost")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body(jwtResponse);
    }

    @GetMapping("/sign-out")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwtToken", null); // Not necessary, but saves bandwidth.
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
        response.addCookie(cookie);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

}
