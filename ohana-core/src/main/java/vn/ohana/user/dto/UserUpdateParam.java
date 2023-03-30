package vn.ohana.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import vn.ohana.entities.Role;
import vn.ohana.entities.UserStatus;

import java.time.Instant;

@Data
public class UserUpdateParam extends BaseUser {
    private Long id;

    private String fullName;

    private String email;
    private String phone;

    private String description;

    private String address;

    private Role role;

    private String password;

    private Instant lastLogin;

    private String thumbnailId;

    private UserStatus status;
}
