package vn.ohana.category.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
public class UpdateCategoryParam extends BaseCategory {
    @NotNull
    private Long id;
}
