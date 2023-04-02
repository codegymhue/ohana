package vn.ohana.controllers.ohana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.LoginResult;


@Controller
@RequestMapping("/")
public class OhanaControllers {

    @Autowired
    UserService userService;

    @GetMapping("/search")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("/ohana/search-bar");
        return modelAndView;
    }
}
