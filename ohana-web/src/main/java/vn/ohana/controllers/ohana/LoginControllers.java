package vn.ohana.controllers.ohana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.ohana.entities.Role;
import vn.ohana.entities.User;
import vn.ohana.entities.UserStatus;
import vn.ohana.google.GoogleService;
import vn.ohana.google.dto.GGSignInParam;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.jwt.JwtResponse;
import vn.ohana.jwt.JwtService;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.*;
import vn.rananu.shared.controllers.BaseController;
import vn.rananu.shared.exceptions.ValidationException;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

@Controller
public class LoginControllers extends BaseController {
    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    GoogleService googleService;

    @GetMapping("/sign-up")
    public ModelAndView signUp() {
        SignUpParam signUpParam = new SignUpParam();
        ModelAndView modelAndView = new ModelAndView("/ohana/sign-up");
        modelAndView.addObject("signUpParam", signUpParam);
        return modelAndView;
    }

    @GetMapping("/sign-in")
    public Object signIn(@CookieValue(value = "CookieSignUp", defaultValue = "0") String loginUsername, Model model) {
        if (!loginUsername.equals("0")) {
            model.addAttribute("success", true);
        }
        LoginParam loginParam = new LoginParam();
        ModelAndView modelAndView = new ModelAndView("/ohana/sign-in");
        modelAndView.addObject("loginParam", loginParam);
        return modelAndView;
    }

    //    @PostMapping("/sign-in")
//    public Object doLogin(@ModelAttribute GGSignInParam ggSignInParam, @ModelAttribute LoginParam loginParam, BindingResult bindingResult, HttpServletResponse response, Model model) throws GeneralSecurityException, IOException {
//        ModelAndView modelAndView = new ModelAndView("/ohana/sign-in");
//        LoginResult loginResult = null;
//        Cookie cookie;
//
//        if (ggSignInParam.getCredential() == null) {
//
//            new LoginParam().validate(loginParam, bindingResult);
//            if (bindingResult.hasFieldErrors()) {
//                return modelAndView;
//            }
//
//            loginResult = userService.findByEmailAndPassword(loginParam.getEmail(), loginParam.getPassword());
//            if (loginResult != null) {
//                if (loginResult.getStatus().equals(UserStatus.CONFIRM_EMAIL)) {
//                    model.addAttribute("confirmMail", true);
//                    return modelAndView;
//                }
//                cookie = new Cookie("cookie", loginParam.getEmail());
//                cookie.setMaxAge(24 * 60 * 60 * 30);
//                response.addCookie(cookie);
//
//                cookie = new Cookie("cookieLogin", loginParam.getEmail());
//                cookie.setMaxAge(2);
//                response.addCookie(cookie);
//                return "redirect:/";
//            } else {
//                model.addAttribute("error", true);
//                model.addAttribute("messages", "Sai email hoặc mật khẩu");
//                return modelAndView;
//            }
//        } else {
//            loginGoogle(ggSignInParam, response, model);
//            return "redirect:/";
//        }
//    }
    @PostMapping("/sign-in")
    public Object doLoginWithCredential(@ModelAttribute GGSignInParam ggSignInParam, @ModelAttribute LoginParam loginParam, BindingResult bindingResult, HttpServletResponse response, Model model) throws GeneralSecurityException, IOException {
        ModelAndView modelAndView = new ModelAndView("/ohana/sign-in");
        LoginResult loginResult = null;
        Cookie cookie;

        if (ggSignInParam.getCredential() == null) {

            new LoginParam().validate(loginParam, bindingResult);
            if (bindingResult.hasFieldErrors()) {
                return modelAndView;
            }

            String username = loginParam.getEmail();
            UserPrincipal userDetails = userService.findUserPrincipleByEmail(username);
            if (userDetails != null) {
                String password = loginParam.getPassword();

                if (userDetails.getStatus().equals(UserStatus.CONFIRM_EMAIL)) {
                    model.addAttribute("confirmMail", true);
                    return modelAndView;
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
                try {
                    authenticationManager.authenticate(authentication);
                } catch (AuthenticationException e) {
//                    throw new ValidationException("login.exception.emailOrPwd");
                    model.addAttribute("error", true);
                    model.addAttribute("messages", "Sai email hoặc mật khẩu.");
                    return modelAndView;
                }
                String jwt = jwtService.generateToken(userDetails);
                cookie = new Cookie("jwtToken", jwt);
                cookie.setMaxAge(60 * 1000);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setSecure(false);
                response.addCookie(cookie);

                cookie = new Cookie("cookieLogin", userDetails.getEmail());
                cookie.setMaxAge(2);
                response.addCookie(cookie);
                return "redirect:/";
            }
            else {
                model.addAttribute("error", true);
                model.addAttribute("messages", "Sai email hoặc mật khẩu");
                return modelAndView;
            }
        } else {
            loginGoogle(ggSignInParam, response, model);
            return "redirect:/";
        }
    }

    public Object loginGoogle(GGSignInParam ggSignInParam, HttpServletResponse response, Model model) {
        try {
            UserDetails userDetails = null;
            Cookie cookie;
            GooglePojo googlePojo = googleService.verifyToken(ggSignInParam.getCredential());
            userDetails = userService.loadUserByUsername(googlePojo.getEmail());
            if (userDetails == null) {
                userDetails = userService.signUpByGoogle(googlePojo);
            }
            String jwt = jwtService.generateToken(userDetails);
            cookie = new Cookie("jwtToken", jwt);
            cookie.setMaxAge(60 * 1000);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            response.addCookie(cookie);

            cookie = new Cookie("cookieLogin", googlePojo.getEmail());
            cookie.setMaxAge(2);
            response.addCookie(cookie);
        } catch (Exception e) {
            model.addAttribute("messages", "Dang nhap google khong thanh cong");
            return "redirect:/sign-in";
        }
        return "redirect:/";
    }

    @PostMapping("/sign-up")
    public Object doSignUp(HttpServletRequest request, @ModelAttribute GGSignInParam ggSignInParam, @ModelAttribute SignUpParam signUpParam , BindingResult bindingResult,Model model, HttpServletResponse response)  throws GeneralSecurityException, IOException{
        System.out.println(request.getRequestURL().toString());
        String url = request.getRequestURL().toString();
        ModelAndView modelAndView = new ModelAndView("/ohana/sign-up");
        Cookie cookie;
        new SignUpParam().validate(signUpParam, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        }

        if (ggSignInParam.getCredential() == null) {
                boolean exists = userService.existsByEmail(signUpParam.getEmail());
                if (exists) {
                    model.addAttribute("error", true);
                    model.addAttribute("messages", "Email đã tồn tại tron hệ thống");
                    return modelAndView;
                }

                UserResult userResult = userService.signUp(url, signUpParam);

                cookie = new Cookie("cookie", userResult.getEmail());
                cookie.setMaxAge(24 * 60 * 60 * 30);
                response.addCookie(cookie);

                cookie = new Cookie("CookieSignUp", userResult.getEmail());
                cookie.setMaxAge(5);
                response.addCookie(cookie);

                return "redirect:/sign-in";
        } else {
            loginGoogle(ggSignInParam, response, model);
            return "redirect:/";
        }

    }

    @GetMapping("/verify-success")
    public ModelAndView verifySuccess() {
        ModelAndView modelAndView = new ModelAndView("/ohana/verify-success");
        return modelAndView;
    }

    @GetMapping("/verify-fail")
    public ModelAndView verifyFail() {
        ModelAndView modelAndView = new ModelAndView("/ohana/verify-fail");
        return modelAndView;
    }


    @GetMapping("/info-forget-password")
    public ModelAndView infoFogetPassword() {
        ModelAndView modelAndView = new ModelAndView("/ohana/info-forget-password");
        return modelAndView;
    }

    @GetMapping("/forget-password")
    public ModelAndView forgetPassword() {
        ModelAndView modelAndView = new ModelAndView("/ohana/forget-password");
        return modelAndView;
    }

    @PostMapping("/forget-password")
    public Object forgetPassword(HttpServletRequest req) throws MessagingException, UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView("/ohana/forget-password");
        String email = req.getParameter("email");
        UserResult userResult = userService.findByEmail(email);

        if (userResult != null) {
            userService.forgetPassword(userResult);
            return "redirect:/info-forget-password";
        } else {
            modelAndView.addObject("error", true);
            return modelAndView;
        }

    }


    @GetMapping("/sign-up/verify")
    public Object doVerify(@Param("code") String code, @ModelAttribute GGSignInParam ggSignInParam, @ModelAttribute SignUpParam signUpParam) {
        boolean user = userService.findByCode(code);
        if (user == true) {
            return "redirect:/verify-success";
        } else {
            return "redirect:/verify-fail";
        }
    }


    @GetMapping("/sign-out")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("cookie", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setComment("EXPIRING COOKIE at " + System.currentTimeMillis());
        response.addCookie(cookie);
        return "redirect:/";
    }


}
