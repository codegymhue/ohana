package vn.ohana.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.Post;
import vn.ohana.entities.StatusPost;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface PostService {

    Page<PostResult> findAll(Pageable pageable);

    Map<Long, String> modifyStatusPostByIds(Set<Long> ids, String published);

    Map<Long, String> notModifyStatusPostByIds(Set<Long> ids, String refused);

    void postEdit(PostUpdateParam postUpdateParam);
}
