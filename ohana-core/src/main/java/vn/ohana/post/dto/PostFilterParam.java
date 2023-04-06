package vn.ohana.post.dto;

import lombok.Getter;
import lombok.Setter;
import vn.ohana.entities.Gender;
import vn.ohana.entities.Location;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.User;
import vn.ohana.location.dto.LocationParam;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
public class PostFilterParam {
    private User user;
    private String keyword;
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private StatusPost status;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Location location;
    private List<Integer> utilities;

}
