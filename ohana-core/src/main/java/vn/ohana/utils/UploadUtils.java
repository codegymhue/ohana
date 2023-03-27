package vn.ohana.utils;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;
import vn.ohana.entities.PostMedia;
import vn.rananu.shared.exceptions.ValidationException;

import java.util.Map;

@Component
public class UploadUtils {
    public static final String IMAGE_UPLOAD_FOLDER = "post_images";

    public Map buildImageUploadParams(PostMedia postMedia) {
//        if (postMedia == null || postMedia.getId() == null)
//            throw new DataInputException("Không thể upload hình ảnh của sản phẩm chưa được lưu");

        String publicId = String.format("%s/%s", IMAGE_UPLOAD_FOLDER, postMedia.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

    public Map buildImageDestroyParams(PostMedia postMedia, String publicId) {
        if (postMedia == null || postMedia.getId() == null)
            throw new ValidationException("Không thể destroy hình ảnh của sản phẩm không xác định");

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }



}
