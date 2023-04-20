package vn.ohana.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import vn.ohana.user.UserService;
import vn.ohana.user.dto.UserPrincipal;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    UserService userService;

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /*
     * Phương thức này được sử dụng để lấy ra các role của user hiện tại đang đăng nhập
     * và trả về URL tương ứng
     */
    protected String determineTargetUrl(Authentication authentication) {
        String url = "";

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (isDba(roles)) {
            //Nếu là tài khoản đăng nhập có role là DBA thì điều hướng đến /dba
            url = "/dba";
        } else if (isAdmin(roles)) {
            //Nếu là tài khoản đăng nhập có role là ADMIN thì điều hướng đến /admin
            url = "/admin";
        } else if (isUser(roles)) {
            //Nếu là tài khoản đăng nhập có role là USER thì điều hướng đến /home
            url = "/home";
        } else {
            //Nếu tài khoản đăng nhập không có quyền truy cập sẽ điều hướng tới /accessDenied
            url = "/accessDenied";
        }

        return url;
    }

    private boolean isUser(List<String> roles) {
        if (roles.contains("ROLE_USER")) {
            return true;
        }
        return false;
    }

    private boolean isAdmin(List<String> roles) {
        if (roles.contains("ROLE_ADMIN")) {
            return true;
        }
        return false;
    }

    private boolean isDba(List<String> roles) {
        if (roles.contains("ROLE_DBA")) {
            return true;
        }
        return false;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        request.setAttribute("userLogin", userService.findByEmail(principal.getEmail()));
        request.getRequestDispatcher("/").forward(request, response);
    }
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
//
//    public Object loginGoogle(GGSignInParam ggSignInParam, HttpServletResponse response, Model model) throws GeneralSecurityException, IOException {
//        try {
//            UserResult userResult = null;
//            Cookie cookie;
//            GooglePojo googlePojo = googleService.verifyToken(ggSignInParam.getCredential());
//            userResult = userService.findByEmail(googlePojo.getEmail());
//
//            if (userResult != null) {
////                thông báo vào trang index
//            } else {
//                userResult = userService.signUpByGoogle(googlePojo);
//            }
//            cookie = new Cookie("cookie", userResult.getEmail());
//            cookie.setMaxAge(24 * 60 * 60 * 30);
//            response.addCookie(cookie);
//
//            cookie = new Cookie("cookieLogin", userResult.getEmail());
//            cookie.setMaxAge(2);
//            response.addCookie(cookie);
//        } catch (Exception e) {
//            model.addAttribute("messages", "Dang nhap google khong thanh cong");
//            return "redirect:/sign-in";
//        }
//        return "redirect:/";
//    }

}