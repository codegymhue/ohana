package vn.ohana.controllers.ohana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import vn.ohana.post.PostService;
import vn.ohana.post.dto.PostCreateParam;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.UserResult;
import vn.ohana.user.dto.UserUpdateParam;
import vn.rananu.shared.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;


@Controller
@RequestMapping("/")
public class OhanaControllers {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @ModelAttribute("userResult")
    public UserResult getUserLoginFromCookie(@CookieValue(value = "cookie", defaultValue = "0") String loginUsername) {
        UserResult userResult = null;
        if (!loginUsername.equals("0")) {
            userResult = userService.findByEmail(loginUsername);
        }
        return userResult;
    }

    @GetMapping("/gmail")
    public ModelAndView gmail() {
        return new ModelAndView(new RedirectView("http://gmail.com"));
    }

    @GetMapping("/")
    public ModelAndView home(@ModelAttribute("userResult") UserResult userResult, @CookieValue(value = "cookieLogin", defaultValue = "0") String loginUsername) {
        ModelAndView modelAndView = new ModelAndView("/ohana/index");
        if (!loginUsername.equals("0")) {
            modelAndView.addObject("success", true);
        }
        modelAndView.addObject("userResult", userResult);
        return modelAndView;
    }

    @GetMapping("/myinfo")
    public Object myInfo(@ModelAttribute("userResult") UserResult userResult) {
        ModelAndView modelAndView = new ModelAndView("/ohana/my-info");
        if (userResult != null) {
            UserUpdateParam userUpdateParam = userService.findByEmailUpdate(userResult.getEmail());
            modelAndView.addObject("userResult", userResult);
            modelAndView.addObject("userUpdateParam", userUpdateParam);
            return modelAndView;
        } else {
            return "/ohana/error";
        }
    }

    @PostMapping("/myinfo")
    public Object doMyInfo(@ModelAttribute("userUpdateParam") UserUpdateParam userUpdateParam, BindingResult bindingResult) throws GeneralSecurityException, IOException {

        ModelAndView modelAndView = new ModelAndView("/ohana/my-info");
        new UserUpdateParam().validate(userUpdateParam, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("userUpdateParam", userUpdateParam);
            modelAndView.addObject("error", true);
            return modelAndView;
        }

        if (userService.existsByPhoneAndIdNot(userUpdateParam.getPhone(), userUpdateParam.getId())) {
            modelAndView.addObject("userUpdateParam", userUpdateParam);
            modelAndView.addObject("errorDupliPhone", true);
            modelAndView.addObject("message", "Số điện thoại này đã được đăng kí");
            return modelAndView;
        }

        if (userService.existsByEmailAndIdNot(userUpdateParam.getEmail(), userUpdateParam.getId())) {
            modelAndView.addObject("userUpdateParam", userUpdateParam);
            modelAndView.addObject("errorDupliEmail", true);
            modelAndView.addObject("message", "Email này đã được đăng kí");
            return modelAndView;
        }


        if (userUpdateParam != null) {
            userService.save(userUpdateParam);
            modelAndView.addObject("success", true);
            UserResult userResult = userService.findByEmail(userUpdateParam.getEmail());

            modelAndView.addObject("userResult", userResult);
            modelAndView.addObject("userUpdateParam", userUpdateParam);
            return modelAndView;
        } else {
            return "/ohana/error";
        }
    }

    @GetMapping("/password")
    public Object password(@ModelAttribute("userResult") UserResult userResult) {
        ModelAndView modelAndView = new ModelAndView("/ohana/password");
        if (userResult != null) {
        UserUpdateParam userUpdateParam = new UserUpdateParam();
        modelAndView.addObject("userUpdateParam", userUpdateParam);
        return modelAndView;
        } else {
            return "/ohana/error";
        }
    }

    @PostMapping("/password")
    public Object doPassword(@ModelAttribute("userResult") UserResult userResult, @ModelAttribute UserUpdateParam userUpdateParam, HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView("/ohana/password");
        String oldPassword = userService.findUserPasswordById(userResult.getId());

        String currentPassword = req.getParameter("currentPassword");
        String newpassword = req.getParameter("newpassword");
        System.out.println(newpassword);

        if (!currentPassword.equals(oldPassword)) {
            modelAndView.addObject("error", true);
            modelAndView.addObject("userUpdateParam", userUpdateParam);
            return modelAndView;
        }

        if (newpassword.equals(oldPassword)) {
            modelAndView.addObject("errorDuplicate", true);
            modelAndView.addObject("userUpdateParam", userUpdateParam);
            return modelAndView;
        }
        else{
            userUpdateParam.setPassword(newpassword);
            userUpdateParam.setId(userResult.getId());
            userService.savePassword(userUpdateParam);
            modelAndView.addObject("success", true);
            modelAndView.addObject("userUpdateParam", userUpdateParam);
            return modelAndView;
        }
    }

    @GetMapping("/post-room")
    public Object postRoom(@ModelAttribute("userResult") UserResult userResult) {
        ModelAndView modelAndView = new ModelAndView("/ohana/post-room");
        if (userResult != null) {
            PostCreateParam postCreateParam = new PostCreateParam();
            modelAndView.addObject("userUpdateParam", userResult);
            modelAndView.addObject("postCreateParam", postCreateParam);
            return modelAndView;
        } else {
            return "/ohana/error";
        }
    }

    @GetMapping("/search")
    public ModelAndView search() {
        ModelAndView modelAndView = new ModelAndView("/ohana/view-all");
        return modelAndView;
    }

//    @GetMapping("/edit-room")
//    public ModelAndView editRoom() {
//        ModelAndView modelAndView = new ModelAndView("/ohana/edit-room");
//        return modelAndView;
//    }

    @GetMapping("/{pId}/edit-room")
    public Object editRoom(@ModelAttribute("userResult") UserResult userResult, @PathVariable Long pId) {
        ModelAndView modelAndView = new ModelAndView("/ohana/edit-room");
        PostResult post = postService.getById(pId);
        if (userResult != null) {
            PostUpdateParam postUpdateParam = new PostUpdateParam();
            modelAndView.addObject("userUpdateParam", userResult);
            modelAndView.addObject("postUpdateParam", postUpdateParam);
            modelAndView.addObject("post", post);
            return modelAndView;
        } else {
            return "/ohana/error";
        }
    }

    @GetMapping("/list-post")
    public Object listPost(@ModelAttribute("userResult") UserResult userResult) {
        ModelAndView modelAndView = new ModelAndView("/ohana/list-post");
        if (userResult != null) {
            return modelAndView;
        } else {
            return "/ohana/error";
        }
    }

    @GetMapping("/{pId}/room")
    public ModelAndView showRoom(@PathVariable Long pId, RedirectAttributes redirectAttributes) {
        try {
            PostResult post = postService.getById(pId);
            ModelAndView modelAndView = new ModelAndView("/ohana/room");
            modelAndView.addObject("post", post);
            return modelAndView;
        } catch (NotFoundException e) {
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("error", "ID không hợp lệ");
            return modelAndView;
        }
    }


}
