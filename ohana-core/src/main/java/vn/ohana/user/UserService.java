package vn.ohana.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.User;
import vn.ohana.entities.UserStatus;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.report.dto.DateReportResult;
import vn.ohana.user.dto.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface UserService  {
    Page<UserResult> getAll(Pageable pageable);

    UserResult update(UserUpdateParam updateParam);

    Page<UserResult> filter(UserFilterParam filter, Pageable pageable);

//   void deactivateAllByIds(Long[] ids);

    Map<String, List<Long>> modifyStatusByIds(Set<Long> ids, String status);

    @Transactional
    boolean modifyStatusById(Long id, String statusRaw);

    UserResult getById(Long id);

    UserDetails signUpByGoogle(GooglePojo googlePojo);

    UserResult signUp(String url, SignUpParam signUpParam) throws MessagingException, UnsupportedEncodingException;

    boolean existsByPhoneOrEmail(String phoneOrEmail);


    LoginResult findByEmailAndPassword(String email, String password);

    LoginResult findByEmailAndPasswordMapper(String email, String password);

    UserResult findByEmailAndPasswordUserResult(String email, String password);

    boolean existsByPhoneOrEmail(String phone, String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneAndIdNot(String phone, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    UserResult findByEmail(String email);

    UserUpdateParam findByEmailUpdate(String email);

//    String findUserPasswordById(Long id);

    UserResult savePassword(UserUpdateParam userUpdateParam);

    User findById(Long userId);

    UserResult save(UserUpdateParam userUpdateParam);

    boolean findByCode(String code);

    void sendMailSignUp(String url, UserResult UserResult) throws MessagingException, UnsupportedEncodingException;

    void forgetPassword(UserResult UserResult) throws MessagingException, UnsupportedEncodingException;

    void sendMailForgetPassword(User user,String tempPwd) throws MessagingException, UnsupportedEncodingException;

    UserPrincipal findUserPrincipleByEmail(String username);

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Long count();

    Long countUsersByStatus(UserStatus status);

    List<Object> countByMonthBetweenDate(Instant startDate, Instant endDate);
}
