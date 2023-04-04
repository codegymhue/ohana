package vn.ohana.post.dto;

import lombok.Getter;
import lombok.Setter;
import vn.ohana.entities.Gender;
import vn.ohana.entities.Location;
import vn.ohana.location.dto.LocationParam;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;


@Getter
@Setter
public class PostFilterParam {
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Location location;

}
