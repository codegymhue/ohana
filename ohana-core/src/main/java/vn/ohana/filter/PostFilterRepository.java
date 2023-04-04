package vn.ohana.filter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ohana.entities.Post;
import vn.ohana.post.dto.PostFilter;

public interface PostFilterRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
//    default Page<Post> findAllByFilters(PostFilter filter, Pageable pageable) {
//        return findAll((root, criteriaQuery, criteriaBuilder)-> {
//
//        }, pageable);
//    }

    Page<Post> findAll(Object o, Pageable pageable);

}
