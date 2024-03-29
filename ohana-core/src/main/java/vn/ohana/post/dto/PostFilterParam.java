package vn.ohana.post.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import vn.ohana.entities.Gender;
import vn.ohana.entities.Location;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.User;
import vn.ohana.location.dto.LocationParam;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PostFilterParam {
    private User user;
    private String keyword;
    private List<Long> gender;
    @Enumerated(EnumType.STRING)
    private StatusPost status;
    private List<Long> categories;
    private BigDecimal priceStarts;
    private BigDecimal priceEnds;
    private Instant createdAtStart;
    private Instant createdAtEnd;
    private Location locationFilter;
    private List<Integer> utilities;
    private Integer limit;
}
