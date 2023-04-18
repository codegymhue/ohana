package vn.ohana.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.Post;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.User;
import vn.ohana.post.dto.PostResult;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    Page<Post> findByUser(User user, Pageable pageable);

    @Query(value = "SELECT * \n" +
            "FROM post p \n" +
            "where p.status = \"PUBLISHED\"\n" +
            "ORDER BY p.created_at " +
            "LIMIT 10;", nativeQuery = true)
    List<Post> getPostsNew();


    Page<Post> findAllByStatusAndUser(StatusPost statusPost, User user, Pageable pageable);

    Page<Post> getEmailById(Long pId);
}
