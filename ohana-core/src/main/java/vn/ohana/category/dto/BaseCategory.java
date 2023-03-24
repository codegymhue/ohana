package vn.ohana.category.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public abstract class BaseCategory {
    @NotBlank
    private String title;
}
