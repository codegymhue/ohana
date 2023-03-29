package vn.ohana.category.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
public class UpdateCategoryParam extends BaseCategory {
    @NotNull
    private Long id;
}
