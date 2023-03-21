package vn.ohana.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import vn.ohana.location.dto.LocationParam;
import vn.ohana.renthouse.dto.RentHouseParam;

import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@Setter
@Getter
public class PostCreateParam {

    @NotNull(message = "The title is required")
    private String title;
    @NotNull(message = "The categoryId is required")
    private Long categoryId;
    private String description;
    private MultipartFile thumbnail;
    private List<MultipartFile> images;
    private LocationParam location;
    private RentHouseParam rentHouse;
    private Long poster;
    private List<Long> utilities;

}
