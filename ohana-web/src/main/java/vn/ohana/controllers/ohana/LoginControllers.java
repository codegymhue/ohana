package vn.ohana.controllers.ohana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.ohana.google.GoogleService;
import vn.ohana.google.dto.GGSignInParam;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.LoginParam;
import vn.ohana.user.dto.LoginResult;
import vn.ohana.user.dto.SignUpParam;
import vn.ohana.user.dto.UserResult;
import vn.rananu.shared.controllers.BaseController;
import vn.rananu.shared.exceptions.ValidationException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;

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

    @PostMapping("/sign-in")
    public Object doLogin( @ModelAttribute GGSignInParam ggSignInParam, @ModelAttribute LoginParam loginParam, BindingResult bindingResult, HttpServletResponse response, Model model) throws GeneralSecurityException, IOException {
        ModelAndView modelAndView = new ModelAndView("/ohana/sign-in");
        LoginResult loginResult = null;
        Cookie cookie;

        if (ggSignInParam.getCredential() == null) {

            new LoginParam().validate(loginParam, bindingResult);
            if (bindingResult.hasFieldErrors()) {
                return modelAndView;
            }

            loginResult = userService.findByEmailAndPassword(loginParam.getEmail(), loginParam.getPassword());
            if (loginResult != null) {
                cookie = new Cookie("cookie", loginParam.getEmail());
                cookie.setMaxAge(24 * 60 * 60 * 30);
                response.addCookie(cookie);

                cookie = new Cookie("cookieLogin", loginParam.getEmail());
                cookie.setMaxAge(3);
                response.addCookie(cookie);
                return "redirect:/";
            }
            else {
                model.addAttribute("error", true);
                model.addAttribute("messages", "Sai email hoặc mật khẩu");
                return modelAndView;
            }
        }
        else {
            loginGoogle(ggSignInParam, response, model);
            return "redirect:/";
        }
    }

    public Object loginGoogle(GGSignInParam ggSignInParam, HttpServletResponse response, Model model) throws GeneralSecurityException, IOException {
        try {
            LoginResult loginResult = null;
            Cookie cookie;
            GooglePojo googlePojo = googleService.verifyToken(ggSignInParam.getCredential());
            loginResult = userService.findByEmail(googlePojo.getEmail());

            if (loginResult != null) {
//                thông báo vào trang index
            } else {
                loginResult = userService.signUpByGoogle(googlePojo);
            }
            cookie = new Cookie("cookie", loginResult.getEmail());
            cookie.setMaxAge(24 * 60 * 60 * 30);
            response.addCookie(cookie);

            cookie = new Cookie("cookieLogin", loginResult.getEmail());
            cookie.setMaxAge(3);
            response.addCookie(cookie);
        } catch (Exception e) {
            model.addAttribute("messages", "Dang nhap google khong thanh cong");
            return "redirect:/sign-in";
        }
        return "redirect:/";
    }

    @PostMapping("/sign-up")
    public Object doSignUp(@ModelAttribute GGSignInParam ggSignInParam, @ModelAttribute SignUpParam signUpParam, Model model, HttpServletResponse response) throws GeneralSecurityException, IOException {
        ModelAndView modelAndView = new ModelAndView("/ohana/sign-up");
        Cookie cookie;

        if (ggSignInParam.getCredential() == null) {
            try {
                UserResult userResult = userService.signUp(signUpParam);

                cookie = new Cookie("cookie", userResult.getEmail());
                cookie.setMaxAge(24 * 60 * 60 * 30);
                response.addCookie(cookie);

                cookie = new Cookie("CookieSignUp", userResult.getEmail());
                cookie.setMaxAge(5);
                response.addCookie(cookie);

                return "redirect:/sign-in";
            } catch (Exception e) {
                System.out.println(e.getMessage());
                model.addAttribute("error", true);
                model.addAttribute("messages", e.getMessage());
                return modelAndView;
            }
        } else {
            loginGoogle(ggSignInParam, response, model);
            return "redirect:/";
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
