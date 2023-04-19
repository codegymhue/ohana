package vn.ohana.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ohana.entities.StatusPost;
import vn.ohana.location.dto.LocationParam;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateParam {
    private Long id;
    private Long userId;
    private String title;
    private Long categoryId;
    private String description;
    private PostMediaParam postMediaThumbnail;
    private List<PostMediaParam> postMediaImages;
    private LocationParam location;
    private RentHouseParam rentHouse;
    private Long poster;
    private List<Long> utilities;
    private StatusPost status;
}
