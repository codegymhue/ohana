package vn.ohana.user.dto;

import lombok.Data;
import vn.ohana.entities.Role;
import vn.ohana.entities.UserStatus;
import vn.ohana.location.dto.LocationResult;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Data
public class UserResult {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String description;
    private String address;
    private LocationResult location;
    private Role role;
    private Instant lastLogin;
    private String thumbnailId;
    private UserStatus status;
    private String code;

}
