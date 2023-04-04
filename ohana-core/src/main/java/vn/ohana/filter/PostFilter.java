package vn.ohana.filter;

import vn.ohana.entities.Gender;
import vn.ohana.location.dto.LocationParam;
import java.math.BigDecimal;

public class PostFilter {
    private String filter;
    private Gender gender;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private LocationParam locationParam;
}
