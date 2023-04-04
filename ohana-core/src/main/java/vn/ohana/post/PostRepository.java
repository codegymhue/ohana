package vn.ohana.post;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.Post;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

}
