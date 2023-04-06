package vn.ohana.controllers.ohana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.ohana.google.GoogleService;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.LoginParam;
import vn.ohana.user.dto.LoginResult;
import vn.ohana.user.dto.SignUpParam;
import vn.rananu.shared.controllers.BaseController;
import vn.rananu.shared.exceptions.ValidationException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginControllers extends BaseController {
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
    public Object doLogin(@ModelAttribute LoginParam loginParam, HttpServletResponse response) {

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
    public Object doSignUp(String token, @ModelAttribute("user") @Valid SignUpParam signUpParam, BindingResult bindingResult, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("/ohana/sign-up");
        if (bindingResult.hasErrors()){
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }

        try {
            GooglePojo googlePojo = null;
            if (token != null) {
                googlePojo = googleService.verifyToken(token);
                if (!googlePojo.getEmail().equalsIgnoreCase(signUpParam.getPhoneOrEmail()))
                    throw new ValidationException("Thông tin đăng nhập không hợp lệ");
            }

            if (googlePojo != null) {
                System.out.println("vao day");
//                googlePojo.setPassword(signUpParam.getPassword());
                System.out.println(signUpParam);
                System.out.println(signUpParam.getPassword());
                LoginResult userResult = userService.signUpByGoogle(googlePojo);
                Cookie cookie = new Cookie("loginUser", userResult.getEmail());
                cookie.setMaxAge(24 * 60 * 60 * 30);
                response.addCookie(cookie);
                return "redirect:/";
            } else {
//                Đăng kí bình thường
                userService.signUp(signUpParam);
                return "redirect:/sign-in?email=" + signUpParam.getPhoneOrEmail();
            }
        } catch (Exception ex) {
            modelAndView.addObject("messageExits", ex.getMessage());
            addError(bindingResult, ex);
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            System.out.println(ex.getMessage());
        }
        return modelAndView;
    }


}
