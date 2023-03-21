package vn.ohana.user.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignUpParam {

    private String fullName;

    private String phoneOrEmail;

    private String password;

    @Override
    public String toString() {
        return "SignUpParam{" +
                "fullName='" + fullName + '\'' +
                ", phoneOrEmail='" + phoneOrEmail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
