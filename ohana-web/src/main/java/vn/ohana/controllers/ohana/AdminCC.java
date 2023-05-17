package vn.ohana.controllers.ohana;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping()
public class AdminCC {
    @GetMapping("/admin/**")
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("/admin/index");
        return modelAndView;
    }
}