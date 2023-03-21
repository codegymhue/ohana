package vn.ohana.post;


import vn.ohana.PostMedia;
import vn.ohana.post.dto.PostMediaResult;

import java.util.List;
import java.util.Optional;

public interface PostMediaService  {

    PostMedia save(PostMedia postMedia);

    Optional<PostMedia> findById(String id);

    PostMedia findByFileUrl(String fileUrl);

    List<PostMediaResult> findAllByPost(String thumbnailId,Long postId);

    PostMedia create(PostMedia postMedia);

    void delete(String id);

    boolean existsById(String postMediaId);

    void removeAllByPostId(Long postId);
}
