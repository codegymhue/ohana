package vn.ohana.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import vn.ohana.location.dto.LocationParam;
import vn.rananu.shared.validation.constraints.NullOrNotBlank;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Accessors(chain = true)
public class BaseUser {
    @NotBlank
    private String fullName;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;

    private String description;

    private String address;

    private LocationParam location;
}
