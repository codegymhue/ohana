package vn.ohana.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ohana.entities.User;
import vn.ohana.user.dto.UserPrincipal;
import vn.rananu.shared.exceptions.ValidationException;

@Service
public class UserAuthenticateService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;


    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ValidationException("user.exception.notFound"));
        return userMapper.toUserPrinciple(user);
    }
}
