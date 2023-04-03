package vn.ohana.user.dto;

import lombok.Getter;
import lombok.Setter;
import vn.ohana.entities.Role;
import vn.ohana.entities.UserStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class UserFilterParam {
    private Long id;

    private String keyword;

    private Role role;


    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
