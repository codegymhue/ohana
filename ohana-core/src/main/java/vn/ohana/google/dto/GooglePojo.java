package vn.ohana.google.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class GooglePojo {
    private String email;
    private String fullName;
    private String thumbnailId;

}
