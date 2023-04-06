package vn.ohana.post.dto;

import lombok.Getter;
import lombok.Setter;
import vn.ohana.category.dto.CategoryResult;
import vn.ohana.entities.StatusPost;
import vn.ohana.location.dto.LocationResult;
import vn.ohana.user.dto.UserResult;
import vn.ohana.utility.dto.UtilityResult;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;
import java.util.List;

@Setter
@Getter
public class PostResult {
    private Long id;
    private RentHouseResult rentHouse;
    private String title;
    private String slug;
    private String descriptionContent;
    private Instant createdAt;
    private UserResult user;
    private LocationResult location;
    private PostMediaResult thumbnailUrl;
    private List<PostMediaResult> postMedia;
    private CategoryResult category;
    private String thumbnailId;
    private List<UtilityResult> utilities;

    @Enumerated(EnumType.STRING)
    private StatusPost status;
}
