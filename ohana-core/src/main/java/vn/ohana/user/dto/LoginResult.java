package vn.ohana.user.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResult {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private String thumbnailUrl;

}
