package vn.ohana.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.Post;
import vn.ohana.entities.User;
import vn.ohana.post.dto.PostResult;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    Page<Post> findByUser(User user, Pageable pageable);

}
