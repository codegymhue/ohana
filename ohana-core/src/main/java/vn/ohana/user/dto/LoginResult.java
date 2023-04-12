package vn.ohana.user.dto;

import lombok.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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



}
