package vn.ohana.post;


import org.springframework.stereotype.Component;
import vn.ohana.entities.PostMedia;
import vn.ohana.post.dto.PostMediaParam;
import vn.ohana.post.dto.PostMediaResult;


@Component
public class PostMediaMapper {

    public PostMediaResult toDTO(PostMedia postMedia) {
        PostMediaResult mediaResult = new PostMediaResult();
        mediaResult.setId(postMedia.getId());
        mediaResult.setFileUrl(postMedia.getFileUrl());
        return mediaResult;
    }

    public PostMedia toPost(PostMediaParam postMediaParam) {
        PostMedia postMedia = new PostMedia();
        return postMedia;
    }
}
