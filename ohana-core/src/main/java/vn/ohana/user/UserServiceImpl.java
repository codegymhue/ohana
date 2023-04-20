package vn.ohana.user;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.ohana.entities.PostMedia;
import vn.ohana.entities.Role;
import vn.ohana.entities.User;
import vn.ohana.entities.UserStatus;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.post.PostMediaService;
import vn.ohana.user.dto.*;
import vn.rananu.shared.exceptions.NotFoundException;
import vn.rananu.shared.exceptions.ValidationException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static vn.ohana.config.MailConfig.MY_EMAIL;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    UserFilterRepository userFilterRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PostMediaService postMediaService;

    @Autowired
    @Qualifier("getJavaMailSender")
    public JavaMailSender emailSender;


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

    @Override
    public String findUserPasswordById(Long id) {
        return findById(id).getPassword();
    }

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
            entity.setStatus(status);
            successIds.add(entity.getId());
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
    public void modifyStatusById(Long id, String statusRaw) {
        UserStatus status = UserStatus.parseUserStatus(statusRaw);
        findById(id).setStatus(status);
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
        User user = userRepository.findByEmail(email).orElseThrow(()->new ValidationException("user.exception.notFound"));
        if (user != null) {
            return userMapper.toUserResultDTO(user);
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
    public UserResult save(UserUpdateParam userUpdateParam) {
        User user = findById(userUpdateParam.getId());

        user.setFullName(userUpdateParam.getFullName());
        user.setEmail(userUpdateParam.getEmail());
        user.setPhone(userUpdateParam.getPhone());
        user.setAddress(userUpdateParam.getAddress());
        if (userUpdateParam.getPassword() != null) {
            user.setPassword(userUpdateParam.getPassword());
        }

        user.setDescription(userUpdateParam.getDescription());
        user.setThumbnailId(userUpdateParam.getThumbnailId());
        userRepository.save(user);
        return userMapper.toUserResultDTO(user);
    }

    @Override
    public UserPrinciple findUserPrincipleByEmail(String username) {
        User user =  userRepository.findByEmail(username).orElseThrow(()->new ValidationException("user.exception.notFound"));
        return userMapper.toUserPrinciple(user);
    }


    public UserResult savePassword(UserUpdateParam userUpdateParam) {
        User user = findById(userUpdateParam.getId());
        user.setPassword(userUpdateParam.getPassword());
        userRepository.save(user);
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
    public UserResult signUpByGoogle(GooglePojo googlePojo) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        String pwd = RandomStringUtils.random(15, characters);
        User userCheck =  userRepository.findByEmail(googlePojo.getEmail()).orElseThrow(()->new ValidationException("user.exception.notFound"));
        if (userCheck != null) {
            return userMapper.toUserResultDTO(userCheck);
        } else {
            User user = userMapper.toGooglePojo(googlePojo);
            user.setStatus(UserStatus.ACTIVATED);
            user.setRole(Role.USER);
            user.setPassword(pwd);
            user.setThumbnailId(googlePojo.getThumbnailId());
            User entity = userRepository.save(user);
            return userMapper.toUserResultDTO(entity);
        }
    }

    @Override
    public UserResult signUp(String url, SignUpParam signUpParam) throws MessagingException, UnsupportedEncodingException {
//        check email tồn tại hay chưa
//        Lưu user
//        set các trường mặc định
//        chuyển DTO,send mail và trả về
        boolean exists = existsByEmail(signUpParam.getEmail());
        if (exists) {
            throw new RuntimeException("Email da ton tai");
        } else {
            User user = new User();
            user.setEmail(signUpParam.getEmail());
            user.setFullName(signUpParam.getFullName());
            user.setPassword(signUpParam.getPassWord());
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

    @ResponseBody
    public void sendMailSignUp(String url, UserResult UserResult) throws MessagingException, UnsupportedEncodingException {

        String toAddress = UserResult.getEmail();
        String subject = "XÁC THỰC TÀI KHOẢN OHANA";

        String content = "Kính chào " + UserResult.getFullName() + "," + "<br>"
                + "Vui lòng click vào đường link để xác thực tài khoản:  "
                + url + "/verify?code=" + UserResult.getCode() + "<br>"
                + "Xin chân thành cảm ơn, <br>"
                + "Ohana team.";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(MY_EMAIL, "Ohana");
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        emailSender.send(message);
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

    public void forgetPassword(UserResult userResult) throws MessagingException, UnsupportedEncodingException {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()";
        String pwd = RandomStringUtils.random(15, characters);

        User user = userRepository.findByEmail(userResult.getEmail()).orElseThrow(()->new ValidationException("user.exception.notFound"));
        user.setPassword(pwd);
        userRepository.save(user);

        sendMailForgetPassword(user);

        userResult = userMapper.toUserDTO(user);

    }

    public void sendMailForgetPassword(User user) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String subject = "QUÊN MẬT KHẨU";

        String content = "Kính chào " + user.getFullName() + "," + "<br>"
                + "Mật khẩu mới của bạn là:  "
                + "<b>" + user.getPassword() + "</b> <br>"
                + "Vui lòng quay trở lại trang " + "<a href=http://localhost:8080/sign-in>đăng nhập</a> <br><br>"
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
