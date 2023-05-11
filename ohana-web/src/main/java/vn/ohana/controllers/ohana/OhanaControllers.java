package vn.ohana.controllers.ohana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import vn.ohana.jwt.JwtService;
import vn.ohana.post.PostService;
import vn.ohana.post.dto.PostCreateParam;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.UserResult;
import vn.ohana.user.dto.UserUpdateParam;
import vn.rananu.shared.exceptions.NotFoundException;
import vn.rananu.shared.exceptions.ValidationException;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;


@Controller
@RequestMapping("/")
public class OhanaControllers {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    PostService postService;

    @ModelAttribute("userResult")
    public UserResult getUserLoginFromCookie(@CookieValue(value = "jwtToken", defaultValue = "null") String token) {
        UserResult userResult = null;
        if (token.equals("null")) {
            return userResult;
        }
        String email = jwtService.extractUserName(token);
        if (!token.equals("0")) {
            userResult = userService.findByEmail(email);
        }
        return userResult;
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

    @GetMapping("/terms-and-policy")
    public Object termsAndPolicy() {
        ModelAndView modelAndView = new ModelAndView("/ohana/layout/terms-and-policy");
        return modelAndView;
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
        String currentPassword = req.getParameter("currentPassword");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userResult.getEmail(), currentPassword);

        try {
            authenticationManager.authenticate(authentication);
            String newpassword = req.getParameter("newpassword");
            if (newpassword.equals(currentPassword)) {
                modelAndView.addObject("errorDuplicate", true);
                modelAndView.addObject("userUpdateParam", userUpdateParam);
                return modelAndView;
            } else {
                userUpdateParam.setPassword(newpassword);
                userUpdateParam.setId(userResult.getId());
                userService.savePassword(userUpdateParam);
                modelAndView.addObject("success", true);
                modelAndView.addObject("userUpdateParam", userUpdateParam);
                return modelAndView;
            }

        } catch (Exception e) {
            modelAndView.addObject("error", true);
            modelAndView.addObject("userUpdateParam", userUpdateParam);
            return modelAndView;
        }
    }
    @GetMapping("/post-room")
    public Object postRoom(@ModelAttribute("userResult") UserResult userResult) {
        ModelAndView modelAndView = new ModelAndView("/ohana/post-room");

        if (userResult.getPhone() == null || userResult.getLocation() == null) {
            modelAndView = new ModelAndView("/ohana/my-info");
            UserUpdateParam userUpdateParam = userService.findByEmailUpdate(userResult.getEmail());
            modelAndView.addObject("userResult", userResult);
            modelAndView.addObject("userUpdateParam", userUpdateParam);
            modelAndView.addObject("updateInfo", true);
            return modelAndView;
        }

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
    public ModelAndView showRoom(@PathVariable Long pId, @ModelAttribute("userResult") UserResult userResult, RedirectAttributes redirectAttributes) {
        try {
            PostResult post = postService.getById(pId);
            ModelAndView modelAndView = new ModelAndView("/ohana/room");
            modelAndView.addObject("post", post);
            modelAndView.addObject("userResult", userResult);
            return modelAndView;
        } catch (NotFoundException e) {
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            modelAndView.addObject("userResult", userResult);
            redirectAttributes.addFlashAttribute("error", "ID không hợp lệ");
            return modelAndView;
        }
    }


}
