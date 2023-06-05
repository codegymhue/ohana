package vn.ohana.utility.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UpdateUtilityParam extends BaseUtility {
    private Integer id;
}
