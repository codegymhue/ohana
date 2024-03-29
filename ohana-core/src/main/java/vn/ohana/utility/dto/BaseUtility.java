package vn.ohana.utility.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import vn.ohana.entities.StatusUtility;

@Getter
@Setter
@Accessors(chain = true)
public class BaseUtility {
    private String name;
    private String icon;
    private int priority;
    private StatusUtility status;
}
