package vn.ohana.user.dto;

import lombok.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//@ToString

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult implements Validator {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private String address;
    private String description;
    private String thumbnailUrl;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginResult.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginResult loginResult = (LoginResult) target;

        String fullName = loginResult.getFullName();
        String email = loginResult.getEmail();
        String address = loginResult.getAddress();
        String description = loginResult.getDescription();
        String phone = loginResult.getPhone();


        if (fullName.length() == 0) {
            errors.rejectValue("fullName", "userupdate.validation.fullName.notBlank");
        } else {
            if (fullName.length() < 4 || fullName.length() > 25) {
                errors.rejectValue("fullName", "userupdate.validation.fullName.notBlank");
            }
        }

        if (email.length() == 0) {
            errors.rejectValue("email", "user.validation.email.notBlank");
        } else {
            if (!email.matches("^[\\w]+@([\\w-]+\\.)+[\\w-]{2,6}$")) {
                errors.rejectValue("email", "user.validation.email.notFormat");
            }
        }

        if (address.length() > 255) {
            errors.rejectValue("address", "user.validation.address.length");
        }

        if (description.length() > 255) {
            errors.rejectValue("description", "user.validation.address.description");
        }

        if (!phone.matches("\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})")) {
            errors.rejectValue("phone", "user.validation.phone.notFormat");
        }
    }

}
