package vn.ohana.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignUpParam {

    @NotBlank(message = "{category.validation.title.notBlank}")
    private String fullName;

    private String phoneOrEmail;

    private String password;


}
