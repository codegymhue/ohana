package vn.ohana.utility.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import vn.ohana.StatusUtility;

@Getter
@Setter
@Accessors(chain = true)
public class BaseUtility {
    private String name;
    private StatusUtility hidden;
    private String icon;
    private int priority;
    private StatusUtility status;
}
