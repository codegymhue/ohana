package vn.ohana.controllers.ohana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.ohana.google.GoogleService;
import vn.ohana.google.dto.GGSignInParam;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.LoginParam;
import vn.ohana.user.dto.LoginResult;
import vn.ohana.user.dto.SignUpParam;
import vn.ohana.user.dto.UserResult;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Controller
public class LoginControllers {
    @Autowired
    UserService userService;
    @Autowired
    GoogleService googleService;

    @GetMapping("/sign-up")
    public ModelAndView signUp() {
        SignUpParam signUpParam = new SignUpParam();
        ModelAndView modelAndView = new ModelAndView("/ohana/sign-up");
        modelAndView.addObject("user", signUpParam);
        return modelAndView;
    }

    @GetMapping("/sign-in")
    public Object signIn() {
        LoginParam loginParam = new LoginParam();
        ModelAndView modelAndView = new ModelAndView("/ohana/sign-in");
        modelAndView.addObject("loginParam", loginParam);
        return modelAndView;
    }


    @PostMapping("/sign-in")
    public Object doLogin(@ModelAttribute LoginParam loginParam, HttpServletResponse response) throws GeneralSecurityException, IOException {

        ModelAndView modelAndView = new ModelAndView();
        System.out.println("Email" + loginParam.getPhoneOrEmail());
        System.out.println("password" + loginParam.getPassword());
//        GooglePojo googlePojo = googleService.verifyToken(ggSignInParam.getCredential());
//        LoginResult loginResult = userService.findByEmail(googlePojo.getEmail());
//        Cookie cookie;
//        if (loginResult != null) {
//             cookie = new Cookie("CookieEmail", loginResult.getEmail());
//        } else {
//            LoginResult userResult = userService.signUpByGoogle(googlePojo);
//             cookie = new Cookie("CookieEmail", userResult.getEmail());
//
//        }
//        cookie.setMaxAge(24 * 60 * 60 * 30);
//        response.addCookie(cookie);
//        modelAndView.setViewName("/ohana/index");
        System.out.println("++++++++++++++++++++++++++++++");
        System.out.println("THANH CONG");
        return "redirect:/";

    }

    @PostMapping("/sign-up")
    public Object doSignUp(@ModelAttribute("user") SignUpParam signUpParam, String token, HttpServletResponse response) throws GeneralSecurityException, IOException {
        GooglePojo googlePojo = null;
        ModelAndView modelAndView = new ModelAndView("/ohana/sign-up");
        try {
            if (token != null) {
                googlePojo = googleService.verifyToken(token);
                if (!googlePojo.getEmail().equalsIgnoreCase(signUpParam.getPhoneOrEmail()))
                    throw new RuntimeException("Thông tin đăng nhập không hợp lệ");
            }

            if (googlePojo != null) {
                System.out.println("vao day");
                googlePojo.setPassword(signUpParam.getPassword());
                System.out.println(signUpParam);
                System.out.println(signUpParam.getPassword());
                LoginResult userResult = userService.signUpByGoogle(googlePojo);
                Cookie cookie = new Cookie("loginUser", userResult.getEmail());
                cookie.setMaxAge(24 * 60 * 60 * 30);
                response.addCookie(cookie);
                return "redirect:/";
            } else {
//                Đăng kí bình thường
                UserResult userResult = userService.signUp(signUpParam);
                return "redirect:/sign-in?email=" + signUpParam.getPhoneOrEmail();
            }
        } catch (Exception ex) {
            modelAndView.addObject("messageExits", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return modelAndView;
    }


}
