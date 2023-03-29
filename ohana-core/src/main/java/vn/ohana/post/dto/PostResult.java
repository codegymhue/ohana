package vn.ohana.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ohana.entities.Category;
import vn.ohana.entities.StatusPost;
import vn.ohana.location.dto.LocationResult;
import vn.ohana.utility.dto.UtilityResult;
import vn.ohana.user.dto.UserResult;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    private List<PostMediaResult> imageUrls;
    private Category category;
    private List<UtilityResult> utilities;
    private StatusPost status;
}
