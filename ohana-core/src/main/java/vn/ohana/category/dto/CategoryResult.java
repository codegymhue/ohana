package vn.ohana.category.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CategoryResult extends BaseCategory {
    private Long id;
}
