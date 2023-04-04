<<<<<<< HEAD
package vn.ohana.controllers.ohana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.ohana.google.GoogleService;
import vn.ohana.google.dto.GGSignInParam;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.LoginResult;
import vn.ohana.user.dto.SignUpParam;
import vn.ohana.user.dto.UserResult;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Controller
public class LoginControllers {
    @Autowired
    UserService userService;
    @Autowired
    GoogleService googleService;

    //    @GetMapping("/sign-up")
=======
//package vn.ohana.controllers.ohana;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//import vn.ohana.google.GoogleService;
//import vn.ohana.google.dto.GooglePojo;
//import vn.ohana.user.UserService;
//import vn.ohana.user.dto.LoginResult;
//import vn.ohana.user.dto.SignUpParam;
//import vn.ohana.user.dto.UserResult;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//
//@Controller
//public class LoginControllers {
//    @Autowired
//    UserService userService;
//    @Autowired
//    GoogleService googleService;
//
//    @GetMapping("/sign-up")
>>>>>>> nguyen_dev
//    public ModelAndView signUp() {
//        ModelAndView modelAndView = new ModelAndView("/ohana/sign-up");
//        return modelAndView;
//    }
//
//    @GetMapping("/sign-in")
//    public ModelAndView signIn() {
//        ModelAndView modelAndView = new ModelAndView("/ohana/sign-in");
//        return modelAndView;
//    }
<<<<<<< HEAD
    @GetMapping("/sign-up")
    public ModelAndView signUp(String token) throws GeneralSecurityException, IOException {
        GooglePojo googlePojo = null;
        SignUpParam signUpParam = new SignUpParam();
        if (token != null) {
            googlePojo = googleService.verifyToken(token);
            signUpParam.setFullName(googlePojo.getName());
            signUpParam.setPhoneOrEmail(googlePojo.getEmail());
        }

        ModelAndView modelAndView = new ModelAndView("/ohana/sign-up");
        modelAndView.addObject("user", signUpParam);
        return modelAndView;
    }

    @PostMapping("/sign-in")
    public Object doLoginGmail(@ModelAttribute GGSignInParam ggSignInParam, @CookieValue(value = "loginUser", defaultValue = "0") String loginUsername, HttpServletResponse response, HttpServletRequest request) throws GeneralSecurityException, IOException {
        GooglePojo googlePojo = googleService.verifyToken(ggSignInParam.getCredential());
        ModelAndView modelAndView = new ModelAndView();
        LoginResult loginResult = userService.findByEmail(googlePojo.getEmail());
        if (loginResult != null) {
            modelAndView.setViewName("/ohana/index");
            Cookie cookie = new Cookie("loginUser", loginResult.getEmail());
            cookie.setMaxAge(24 * 60 * 60 * 30);
            response.addCookie(cookie);
            return "redirect:/";
        }
        return String.format("redirect:/sign-up?token=%s", ggSignInParam.getCredential());
    }

    @PostMapping("/sign-up")
    public Object handleSignUp(@ModelAttribute("user") SignUpParam signUpParam, String token, HttpServletResponse response) throws GeneralSecurityException, IOException {
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
                LoginResult userResult = userService.saveGoogleEmail(googlePojo);
                Cookie cookie = new Cookie("loginUser", userResult.getEmail());
                cookie.setMaxAge(24 * 60 * 60 * 30);
                response.addCookie(cookie);
                return "redirect:/";
            } else {
//                Đăng kí bình thường
                UserResult userResult = userService.signUp(signUpParam);
                return "redirect:/sign-in?email=" + signUpParam.getPhoneOrEmail();
            }
//            modelAndView.addObject("message", "Đăng nhập thành công");
//            modelAndView.addObject("userLogin", userResult);
        } catch (Exception ex) {
            modelAndView.addObject("messageExits", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return modelAndView;
    }


}
=======
//
//    @GetMapping("/sign-up")
//    public ModelAndView signUp(String token) throws GeneralSecurityException, IOException {
//        GooglePojo googlePojo = null;
//        SignUpParam signUpParam = new SignUpParam();
//        if (token != null) {
//            googlePojo = googleService.verifyToken(token);
//            signUpParam.setFullName(googlePojo.getName());
//            signUpParam.setPhoneOrEmail(googlePojo.getEmail());
//        }
//        ModelAndView modelAndView = new ModelAndView("/ohana/sign-up");
//        modelAndView.addObject("user", signUpParam);
//        return modelAndView;
//    }
//
//    @PostMapping("/sign-up")
//    public Object handleSignUp(@ModelAttribute("user") SignUpParam signUpParam, String t, HttpServletResponse response) throws GeneralSecurityException, IOException {
//        GooglePojo googlePojo = null;
//        ModelAndView modelAndView = new ModelAndView("/ohana/sign-up");
//        try {
//            if (t != null) {
//                googlePojo = googleService.verifyToken(t);
//                if (!googlePojo.getEmail().equalsIgnoreCase(signUpParam.getPhoneOrEmail()))
//                    throw new RuntimeException("Thông tin đăng nhập không hợp lệ");
//            }
//
//            if (googlePojo != null) {
//                System.out.println("vaof day");
//                googlePojo.setPassword(signUpParam.getPassword());
//                System.out.println(signUpParam);
//                LoginResult userResult = userService.saveGoogleEmail(googlePojo);
//                Cookie cookie = new Cookie("loginUser", userResult.getEmail());
//                cookie.setMaxAge(24 * 60 * 60 * 30);
//                response.addCookie(cookie);
//                return "redirect:/";
//            } else {
////                Đăng kí
////                userService.saveGoogleEmail(googlePojo);
//                UserResult userResult = userService.signUp(signUpParam);
//                return "redirect:/sign-in?email=" + signUpParam.getPhoneOrEmail();
//            }
////            modelAndView.addObject("message", "Đăng nhập thành công");
////            modelAndView.addObject("userLogin", userResult);
//        } catch (Exception ex) {
//            modelAndView.addObject("messageExits", ex.getMessage());
//            System.out.println(ex.getMessage());
//        }
//        return modelAndView;
//    }
//}
>>>>>>> nguyen_dev
