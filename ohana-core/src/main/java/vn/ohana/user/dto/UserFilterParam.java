package vn.ohana.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import vn.ohana.entities.Role;
import vn.ohana.entities.UserStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;

@Getter
@Setter
@Accessors(chain = true)
public class UserFilterParam {
    private Long id;

    private String keyword;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Instant createdAtStart;
    private Instant createdAtEnd;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
