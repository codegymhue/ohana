//package vn.ohana.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import vn.ohana.user.UserServiceImpl;
//
//@Component
//public class SecurityAuthenticateProvider implements AuthenticationProvider {
//
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.isAssignableFrom(SecurityAuthenticateProvider.class);
//    }
//
//}
