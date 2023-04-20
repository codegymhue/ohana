package vn.ohana.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.ohana.entities.Role;
import vn.ohana.entities.UserStatus;

import java.time.Instant;

@Setter
@Getter
public class UserUpdateParam extends BaseUser implements Validator {
    private Long id;
    private String password;
    private String thumbnailId;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserUpdateParam.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateParam userUpdateParam = (UserUpdateParam) target;

        String fullName = userUpdateParam.getFullName();
        String email = userUpdateParam.getEmail();
        String address = userUpdateParam.getAddress();
        String description = userUpdateParam.getDescription();
        String phone = userUpdateParam.getPhone();

        if (fullName.length() == 0) {
            errors.rejectValue("fullName", "userupdate.validation.fullName.notBlank");
        } else {
            if (fullName.length() < 4 || fullName.length() > 25) {
                errors.rejectValue("fullName", "userupdate.validation.fullName.notFormat");
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
            errors.rejectValue("address", "user.validation.address.description");
        }

        if (description.length() > 255) {
            errors.rejectValue("description", "user.validation.address.description");
        }

        if (!phone.matches("^0\\d{7,9}$")) {
            errors.rejectValue("phone", "user.validation.phone.notFormat");
        }
    }
}
