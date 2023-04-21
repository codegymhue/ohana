package vn.ohana.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class PostMediaParam {
    private String id;
    private String publicId;
    private String fileUrl;

}
