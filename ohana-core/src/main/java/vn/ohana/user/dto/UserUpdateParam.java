package vn.ohana.user.dto;

import lombok.Getter;
import lombok.Setter;
import vn.ohana.entities.Role;
import vn.ohana.entities.UserStatus;

import java.time.Instant;

@Setter
@Getter
public class UserUpdateParam extends BaseUser {
    private Long id;
}
