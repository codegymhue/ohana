package vn.ohana.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.ohana.location.dto.LocationParam;

import javax.validation.constraints.NotNull;
import java.util.List;


@ToString
@Setter
@Getter
public class PostUpdatedParam {
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