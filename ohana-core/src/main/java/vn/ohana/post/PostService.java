package vn.ohana.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import vn.ohana.entities.Post;
import vn.ohana.entities.StatusPost;
import vn.ohana.post.dto.PostCreateParam;
import vn.ohana.post.dto.PostFilterParam;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;
import vn.ohana.report.dto.DateReportResult;
import vn.ohana.user.dto.UserUpdateParam;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface PostService {

    Page<PostResult> findAll(Pageable pageable);

    Map<String,Object> modifyStatusByIds(Set<Long> ids, String published);

    Page<PostResult> filter(PostFilterParam filter, Pageable pageable);


    Page<PostResult> findAllByUser(UserUpdateParam user, Pageable pageable);

    Page<PostResult> filterPublishedPosts(PostFilterParam filter, Pageable pageable);

    PostResult getById(Long pId);

    PostResult updateStatusById(PostUpdateParam postUpdateParam);

    PostCreateParam save(PostCreateParam postCreateParam);

    Page<PostResult> getTop10PostsNew(Long id);

    Post findById(Long id);

    Page<PostResult> findAllByStatusAndUser(StatusPost status, Long id, Pageable pageable);

    Page<PostResult> findAllByStatus(StatusPost pendingReview, Pageable createdAt);

    Long count();

    Long countPostByStatus(StatusPost status);
    List<Object> countByMonthBetweenDate(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
}
