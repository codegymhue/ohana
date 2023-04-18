package vn.ohana.user.dto;

import lombok.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginParam implements Validator {

    private String email;

    private String password;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginParam.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        LoginParam loginParam = (LoginParam) target;

        String email = loginParam.getEmail();

        if (email.length() == 0) {
                    errors.rejectValue("email", "user.validation.email.notBlank");
        } else {
            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                errors.rejectValue("email", "user.validation.email.notFormat");
            }
        }
    }
}
