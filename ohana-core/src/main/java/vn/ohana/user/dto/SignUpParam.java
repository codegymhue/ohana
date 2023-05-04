package vn.ohana.user.dto;

import lombok.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Getter
@Setter
public class SignUpParam implements Validator {

    private String fullName;
    private String email;
    private String passWord;
    private String code;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpParam.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpParam signUpParam = (SignUpParam) target;
        String fullName = signUpParam.getFullName();
        String email = signUpParam.getEmail();
        String password = signUpParam.getPassWord();


        if (fullName.length() == 0) {
            errors.rejectValue("fullName", "user.validation.fullName.notBlank");
        } else {
            if (fullName.length() < 4 || fullName.length() > 25) {
                errors.rejectValue("fullName", "fullName.length");
            }
        }

        if (email.length() == 0) {
            errors.rejectValue("email", "user.validation.email.notBlank");
        } else {
            if (!email.matches("^[\\w]+@([\\w-]+\\.)+[\\w-]{2,6}$")) {
                errors.rejectValue("email", "user.validation.email.notFormat");
            }
        }

        if (password.length() == 0) {
            errors.rejectValue("passWord", "user.validation.passWord.notBlank");
        } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-`!~({})|.,*_@#$%^&+=/])(?=\\S+$).{6,50}$")) {
            errors.rejectValue("passWord", "user.validation.passWord.notFormat");
        }

    }
}
