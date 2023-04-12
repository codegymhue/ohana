package vn.ohana.controllers.ohana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.ohana.entities.PostMedia;
import vn.ohana.entities.User;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.LoginParam;
import vn.ohana.user.dto.LoginResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Optional;


@Controller
@RequestMapping("/")
public class OhanaControllers {

    @Autowired
    UserService userService;

    @ModelAttribute("userLogin")
    public LoginResult getUserLoginFromCookie(@CookieValue(value = "cookie", defaultValue = "0") String loginUsername) {
        LoginResult userLogin = null;
        if (!loginUsername.equals("0")) {
            userLogin = userService.findByEmail(loginUsername);
        }
        return userLogin;
    }

    @GetMapping("/")
    public ModelAndView home(@ModelAttribute("userLogin") LoginResult userLogin, @CookieValue(value = "cookieLogin", defaultValue = "0") String loginUsername) {
        ModelAndView modelAndView = new ModelAndView("/ohana/index");
        if (!loginUsername.equals("0")) {
            modelAndView.addObject("success", true);
        }
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }
    @GetMapping("/myinfo")
    public Object myInfo(@ModelAttribute("userLogin") LoginResult userUpdateParam) {
        ModelAndView modelAndView = new ModelAndView("/ohana/my-info");
        if (userUpdateParam != null) {
            modelAndView.addObject("userUpdateParam", userUpdateParam);
            return modelAndView;
        } else {
            return "/ohana/error";
        }
    }

    @PostMapping("/myinfo")
    public Object doMyInfo(@ModelAttribute("userUpdateParam") LoginResult loginResult, BindingResult bindingResult) throws GeneralSecurityException, IOException {
        ModelAndView modelAndView = new ModelAndView("/ohana/my-info");
        new LoginResult().validate(loginResult, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("userUpdateParam", loginResult);
            modelAndView.addObject("error", true);
            return modelAndView;
        }

        if (loginResult != null) {
            loginResult = userService.save(loginResult);
            modelAndView.addObject("success", true);

            modelAndView.addObject("userUpdateParam", loginResult);
            return modelAndView;
        } else {
            return "/ohana/error";
        }
    }

    @GetMapping("/password")
    public Object password(@ModelAttribute("userLogin") LoginResult userUpdateParam ) {
        ModelAndView modelAndView = new ModelAndView("/ohana/password");
        if (userUpdateParam != null) {
            modelAndView.addObject("userUpdateParam", userUpdateParam);
            return modelAndView;
        } else {
            return "/ohana/error";
        }
    }

    @PostMapping("/password")
    public Object doPassword(@ModelAttribute LoginResult userUpdateParam, HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView("/ohana/password");
        String newpassword = req.getParameter("newpassword");
        System.out.println(newpassword);

        LoginResult userUpdateParamNew = userService.findByEmailAndPassword(userUpdateParam.getEmail(),userUpdateParam.getPassword());
        if (userUpdateParamNew != null) {

            if (newpassword.equals(userUpdateParam.getPassword())) {
                modelAndView.addObject("errorDuplicate", true);
                modelAndView.addObject("userUpdateParam", userUpdateParam);
                return modelAndView;
            }
            else {
            userUpdateParamNew.setPassword(newpassword);
            userService.save(userUpdateParamNew);
            modelAndView.addObject("success", true);
            modelAndView.addObject("userUpdateParam", userUpdateParamNew);
            return modelAndView;
            }
        }
        else {
            modelAndView.addObject("error", true);
            modelAndView.addObject("userUpdateParam", userUpdateParam);
            return modelAndView;
        }
    }


    @GetMapping("/search")
    public ModelAndView search() {
        ModelAndView modelAndView = new ModelAndView("/ohana/view-all");
        return modelAndView;
    }



    @GetMapping("/post-room")
    public ModelAndView postRoom() {
        ModelAndView modelAndView = new ModelAndView("/ohana/post-room");
        return modelAndView;
    }


        @GetMapping("/edit-room")
    public ModelAndView editRoom() {
        ModelAndView modelAndView = new ModelAndView("/ohana/edit-room");
        return modelAndView;
    }

    @GetMapping("/list-post")
    public ModelAndView listPost() {
        ModelAndView modelAndView = new ModelAndView("/ohana/list-post");
        return modelAndView;
    }

    @GetMapping("/{pId}/room")
    public ModelAndView room() {
        ModelAndView modelAndView = new ModelAndView("/ohana/room");
        return modelAndView;
    }

}
