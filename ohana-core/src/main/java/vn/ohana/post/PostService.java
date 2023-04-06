package vn.ohana.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.ohana.entities.Post;
import vn.ohana.entities.User;
import vn.ohana.entities.User;
import vn.ohana.post.dto.PostFilterParam;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;

import java.util.Map;
import java.util.Set;


public interface PostService {

    Page<PostResult> findAll(Pageable pageable);

    Map<Long, String> modifyStatusByIds(Set<Long> ids, String published);

    Page<PostResult> filter(PostFilterParam filter, Pageable pageable);
    void postEdit(PostUpdateParam postUpdateParam);

    Page<PostResult> findAllByUser(User user, Pageable pageable);


    PostResult getById(Long pId);
}
