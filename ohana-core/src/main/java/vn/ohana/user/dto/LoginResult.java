package vn.ohana.user.dto;

import lombok.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.ohana.entities.Role;

//@ToString

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private String address;
    private String description;
    private String thumbnailId;
    private Role role;


}
