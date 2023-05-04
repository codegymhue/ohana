package vn.ohana.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.ohana.entities.Location;
import vn.ohana.entities.Role;
import vn.ohana.entities.UserStatus;
import vn.ohana.location.dto.LocationParam;

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
        String description = userUpdateParam.getDescription();
        String phone = userUpdateParam.getPhone();
        LocationParam locationParam = userUpdateParam.getLocation();

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

        if (locationParam == null) {
            errors.rejectValue("location","user.validation.location.null");
        }else {
            if (locationParam.getProvinceId() == 0) {
                errors.rejectValue("location.provinceId","user.validation.location.emptyProvince");
            }
            if (locationParam.getDistrictId() == 0) {
                errors.rejectValue("location.districtId","user.validation.location.emptyDistrict");
            }
            if (locationParam.getWardId() == 0) {
                errors.rejectValue("location.wardId","user.validation.location.emptyWard");
            }
            if (locationParam.getLine1().length() == 0) {
                errors.rejectValue("address", "user.validation.location.addressLength");
            }

            if (locationParam.getLine1().length() > 255) {
                errors.rejectValue("address", "user.validation.location.addressLength");
            }
        }

        if (description.length() > 255) {
            errors.rejectValue("description", "user.validation.address.description");
        }

        if (!phone.matches("^0\\d{7,9}$")) {
            errors.rejectValue("phone", "user.validation.phone.notFormat");
        }
    }
}
