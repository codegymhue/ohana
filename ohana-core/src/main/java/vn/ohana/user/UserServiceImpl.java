package vn.ohana.user;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.ohana.entities.Role;
import vn.ohana.entities.User;
import vn.ohana.entities.UserStatus;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.location.LocationMapper;
import vn.ohana.post.PostMediaService;
import vn.ohana.report.dto.DateReportResult;
import vn.ohana.user.dto.*;
import vn.rananu.shared.exceptions.NotFoundException;
import vn.rananu.shared.exceptions.ValidationException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static vn.ohana.config.MailConfig.MY_EMAIL;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserFilterRepository userFilterRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PostMediaService postMediaService;

    @Autowired
    LocationMapper locationMapper;

    @Autowired
    @Qualifier("getJavaMailSender")
    public JavaMailSender emailSender;

//    public UserServiceImpl(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = new BCryptPasswordEncoder(10);
//    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public boolean existsByPhoneOrEmail(String phone, String email) {
        return false;
    }
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category.exception.notFound"));
    }

    public Page<UserResult> getAll(Pageable pageable) {
        Page<User> page = userRepository.findByRoleNot(Role.ADMIN, pageable);
        return userMapper.toDtoPage(page);
    }

//    @Override
//    public String findUserPasswordById(Long id) {
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
//        try {
//            authenticationManager.authenticate(authentication);
//        } catch (AuthenticationException e) {
//            throw new ValidationException("login.exception.emailOrPwd");
//        }
//        return findById(id).getPassword();
//    }

    @Override
    @Transactional
    public UserResult update(UserUpdateParam updateParam) {
        User user = findById(updateParam.getId());
        userMapper.transferFields(updateParam, user);
        return userMapper.toDTO(user);
    }

    @Override
    public Page<UserResult> filter(UserFilterParam filter, Pageable pageable) {
        Page<User> page = userFilterRepository.findAllByFilters(filter, pageable);
        return userMapper.toDtoPage(page);
    }

    @Override
    @Transactional
    public Map<String, List<Long>> modifyStatusByIds(Set<Long> ids, String statusRaw) {
        UserStatus status = UserStatus.parseUserStatus(statusRaw);
        Map<String, List<Long>> result = new HashMap<>();
        List<Long> successIds = new ArrayList<>();
        List<Long> failIds = new ArrayList<>();
        Iterable<User> entities = userRepository.findAllById(ids);
        entities.forEach(entity -> {
            if (entity.getRole().equals(Role.ADMIN)) {
                failIds.add(entity.getId());
            }else {
                entity.setStatus(status);
                successIds.add(entity.getId());
            }
        });
        result.put("succeed", successIds);

        List<Long> entityIds = StreamSupport.stream(entities.spliterator(), false).map(User::getId).collect(Collectors.toList());
        ids.forEach(id -> {
            if (!entityIds.contains(id)) {
                failIds.add((id));
            }
        });
        result.put("failed", failIds);
        return result;
    }

    @Override
    @Transactional
    public boolean modifyStatusById(Long id, String statusRaw) {
        UserStatus status = UserStatus.parseUserStatus(statusRaw);
        User user = findById(id);
        if (user.getRole().equals(Role.ADMIN)) {
            return false;
        }
        user.setStatus(status);
        return true;
    }

    @Override
    public UserResult getById(Long id) {
        return userMapper.toDTO(findById(id));
    }

    private Page<UserResult> toDtoPage(Page<User> page) {
        return page.map(entity -> userMapper.toDTO(entity));
    }


    @Override
    public UserResult findByEmail(String email) {
        Optional <User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return userMapper.toUserResultDTO(user.get());
        }
        return null;
    }

    @Override
    public UserUpdateParam findByEmailUpdate(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new ValidationException("user.exception.notFound"));
        if (user != null) {
            return userMapper.toUserUpdateParamDTO(user);
        }
        return null;

    }


    @Override
    @Transactional
    public UserResult save(UserUpdateParam userUpdateParam) {

        User user = findById(userUpdateParam.getId());
        user.setFullName(userUpdateParam.getFullName());
        user.setEmail(userUpdateParam.getEmail());
        user.setPhone(userUpdateParam.getPhone());
        user.setLocation(locationMapper.toEntity(userUpdateParam.getLocation()));
        if (userUpdateParam.getPassword() != null) {
            user.setPassword(userUpdateParam.getPassword());
        }
        user.setDescription(userUpdateParam.getDescription());
        user.setThumbnailId(userUpdateParam.getThumbnailId());
        userRepository.save(user);
        return userMapper.toUserResultDTO(user);
    }

    @Override
    public UserPrincipal findUserPrincipleByEmail(String username) {
        Optional<User> user =  userRepository.findByEmail(username);
        if (user.isPresent()) {
            return userMapper.toUserPrinciple(user.get());
        }
        return null;
    }

    @Transactional
    public UserResult savePassword(UserUpdateParam userUpdateParam) {
        User user = findById(userUpdateParam.getId());
        user.setPassword(passwordEncoder.encode(userUpdateParam.getPassword()));
        return userMapper.toUserResultDTO(user);
    }

    @Override
    public LoginResult findByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            return userMapper.toLoginResultDTO(user);
        }
        return null;
    }


    public LoginResult findByEmailAndPasswordMapper(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            return userMapper.toLoginResult(user);
        }
        return null;
    }

    @Override
    public UserResult findByEmailAndPasswordUserResult(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            return userMapper.toUserResultDTO(user);
        }
        return null;
    }

    @Override
    public boolean existsByPhoneOrEmail(String phoneOrEmail) {
        return userRepository.existsByPhoneOrEmail(phoneOrEmail, phoneOrEmail);
    }

    @Override
    public boolean existsByPhoneAndIdNot(String phone,Long id) {
        return userRepository.existsByPhoneAndIdNot(phone,id);
    }

    public boolean existsByEmailAndIdNot(String phone,Long id) {
        return userRepository.existsByEmailAndIdNot(phone,id);
    }

    @Override
    public boolean existsByEmail(String Email) {
        return userRepository.existsByEmail(Email);
    }

    @Override
    @Transactional
    public UserDetails signUpByGoogle(GooglePojo googlePojo) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=";
        String pwd = RandomStringUtils.random(15, characters);
        Optional<User> userCheck =  userRepository.findByEmail(googlePojo.getEmail());
        if (userCheck.isPresent()) {
            return userMapper.toUserPrinciple(userCheck.get());
        } else {
            User user = userMapper.toGooglePojo(googlePojo);
            user.setStatus(UserStatus.ACTIVATED);
            user.setRole(Role.USER);
            user.setPassword(passwordEncoder.encode(pwd));
            user.setThumbnailId(googlePojo.getThumbnailId());
            User entity = userRepository.save(user);
            return userMapper.toUserPrinciple(entity);
        }
    }

    @Override
    public UserResult signUp(String url, SignUpParam signUpParam)  {
//        check email tồn tại hay chưa
//        Lưu user
//        set các trường mặc định
//        chuyển DTO,send mail và trả về
        boolean exists = existsByEmail(signUpParam.getEmail());
        if (exists) {
            throw new RuntimeException("Email đã tồn tại trong hệ thống.");
        } else {
            User user = new User();
            user.setEmail(signUpParam.getEmail());
            user.setFullName(signUpParam.getFullName());
            user.setPassword(passwordEncoder.encode(signUpParam.getPassWord()));
            user.setStatus(UserStatus.CONFIRM_EMAIL);
            user.setRole(Role.USER);
            int code = (int) Math.floor(((Math.random() * 899999) + 100000));
            user.setCode(String.valueOf(code));
            userRepository.save(user);
            UserResult userResult = userMapper.toUserDTO(user);

            sendMailSignUp(url, userResult);

            return userResult;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserPrincipleByEmail(username);
    }

    @Override
    public Long count() {
        return userRepository.count();
    }

    @Override
    public Long countUsersByStatus(UserStatus status) {
        return userRepository.countUsersByStatus(status);
    }

    @Override
    @Transactional
    public List<Object> countByMonthBetweenDate(Instant startDate, Instant endDate) {
        return userRepository.countByMonthBetweenDate(startDate, endDate);
    }


    @ResponseBody
    public void sendMailSignUp(String url, UserResult UserResult)  {

        String toAddress = UserResult.getEmail();
        String subject = "XÁC THỰC TÀI KHOẢN OHANA";

        String content = "Kính chào " + UserResult.getFullName() + "," + "<br>"
                + "Vui lòng click vào đường link để xác thực tài khoản:  "
                + url + "/verify?code=" + UserResult.getCode() + "<br>"
                + "Xin chân thành cảm ơn, <br>"
                + "Ohana team.";

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(MY_EMAIL, "Ohana");
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content, true);
            emailSender.send(message);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public boolean findByCode(String code) {
        User user = userRepository.findByCode(code);

        if (user != null) {
            user.setCode(null);
            user.setStatus(UserStatus.ACTIVATED);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public List<Object> getUserWithMostPosts(int limit) {
        return userRepository.findUserWithMostPosts(limit);
    }
    public void forgetPassword(UserResult userResult) throws MessagingException, UnsupportedEncodingException {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()";
        String pwd = RandomStringUtils.random(15, characters);

        User user = userRepository.findByEmail(userResult.getEmail()).orElseThrow(()->new ValidationException("user.exception.notFound"));
        user.setPassword(passwordEncoder.encode(pwd));
        userRepository.save(user);
        sendMailForgetPassword(user,pwd);
    }

    public void sendMailForgetPassword(User user,String tempPwd) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String subject = "QUÊN MẬT KHẨU";

        String content = "Kính chào " + user.getFullName() + "," + "<br>"
                + "Mật khẩu mới của bạn là:  "
                + "<b>" + tempPwd + "</b> <br>"
                + "Vui lòng quay trở lại trang " + "<a href=https://ohana.cghue.com/sign-in>đăng nhập</a> <br><br>"
                + "Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi,<br> "
                + "Ohana team.";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(MY_EMAIL, "Ohana");
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        emailSender.send(message);
    }

}
