package vn.ohana.controllers.errors;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class User {
    private Long id;

    @NotEmpty(message = "category.validation.title.notBlank")
    @Size(min = 5, max = 250)
    private String fullName;

    @NotEmpty(message = "User's email cannot be empty.")
    @Size(min = 7, max = 320)
    private String email;

    @NotNull(message = "User's age cannot be null.")
    @Min(value = 18)
    private Integer age;

    private String country;

    private String phoneNumber;


}
