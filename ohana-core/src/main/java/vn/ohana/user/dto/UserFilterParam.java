package vn.ohana.user.dto;

import lombok.Getter;
import lombok.Setter;
import vn.ohana.entities.Role;
import vn.ohana.entities.UserStatus;

@Getter
@Setter
public class UserFilterParam {
    private Long id;

    private String keyword;

    private Role role;

    private UserStatus status;
}
