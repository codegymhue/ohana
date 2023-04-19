package vn.ohana.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ohana.entities.StatusPost;
import vn.ohana.location.dto.LocationParam;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateParam {
    private Long id;
    @NotNull(message = "The title is required")
    private String title;
    @NotNull(message = "The categoryId is required")
    private Long categoryId;
    private String descriptionContent;
    private String thumbnailId;
    private List<String> images;
    private LocationParam location;
    private RentHouseParam rentHouse;
    private List<Long> utilities;
    private Long idUser;
}
