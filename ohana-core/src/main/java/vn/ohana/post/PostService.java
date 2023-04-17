package vn.ohana.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.ohana.entities.Post;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.User;
import vn.ohana.post.dto.PostFilterParam;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;
import vn.ohana.user.dto.UserUpdateParam;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface PostService {

    Page<PostResult> findAll(Pageable pageable);

    Map<Long, String> modifyStatusByIds(Set<Long> ids, String published);

    Page<PostResult> filter(PostFilterParam filter, Pageable pageable);
    void postEdit(PostUpdateParam postUpdateParam);

    Page<PostResult> findAllByUser(UserUpdateParam user, Pageable pageable);
    Page<PostResult> filterPublishedPosts(PostFilterParam filter, Pageable pageable);

    PostResult getById(Long pId);

    PostResult updateStatusById(PostUpdateParam postUpdateParam);

    List<PostResult>  getTop10PostsNew();

    Post findById(Long id);

    Page<PostResult> findAllByStatusAndUser(StatusPost status, Long id,Pageable pageable);

}
