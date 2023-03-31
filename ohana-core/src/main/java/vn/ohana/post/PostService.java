package vn.ohana.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.post.dto.PostResult;


public interface PostService {


    Page<PostResult> findAll(Pageable pageable);
}
