package vn.ohana.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.Post;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.User;

import java.time.Instant;
import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    Page<Post> findByUser(User user, Pageable pageable);


    Page<Post> findAllByStatusAndUser(StatusPost statusPost, User user, Pageable pageable);

    Long countPostByStatus(StatusPost status);

    Page<Post> findAllByStatus(StatusPost status, Pageable pageable);


    @Procedure(procedureName = "coutPostByMonhtBetweenDate")
    List<Object> countByMonthBetweenDate(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

}
