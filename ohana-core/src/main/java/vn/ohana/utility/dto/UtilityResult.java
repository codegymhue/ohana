package vn.ohana.utility.dto;

import lombok.Getter;
import lombok.Setter;
import vn.ohana.StatusUtility;

@Getter
@Setter
public class UtilityResult {
    private Long id;
    private String name;
    private StatusUtility hidden;
    private String icon;
    private int priority;
    private StatusUtility status;
}

