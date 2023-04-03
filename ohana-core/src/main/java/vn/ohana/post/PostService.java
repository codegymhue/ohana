package vn.ohana.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.Post;
import vn.ohana.entities.StatusPost;
import vn.ohana.post.dto.PostResult;

import java.util.List;


public interface PostService {

    Page<PostResult> findAll(Pageable pageable);

    void approve(Long id);

    void unApprove(Long id);
}
