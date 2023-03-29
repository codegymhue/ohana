package vn.ohana.category.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Accessors(chain = true)
public abstract class BaseCategory {
    @NotBlank(message = "{category.validation.title.notBlank}")
    private String title;
}
