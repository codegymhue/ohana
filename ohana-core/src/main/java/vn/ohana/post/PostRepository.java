package vn.ohana.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.Post;
import vn.ohana.entities.User;
import vn.ohana.post.dto.PostResult;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    Page<Post> findByUser(User user, Pageable pageable);

    @Query(value = "select distinct json_extract(p.location, '$.provinceName') AS province," +
            "json_extract(p.location, '$.wardName') AS district " +
            "from post p where (JSON_Extract (p.location,\"$.provinceId\") = :provinceId) " +
            "and upper( JSON_Extract (p.location,\"$.wardName\")) like upper(:wardName) " +
            "or upper( JSON_Extract (p.location,\"$.wardUnsignedName\")) like upper(:wardName)", nativeQuery = true)
    List<Post> getDataSearch(@Param("provinceId") String provinceId, @Param("wardName") String wardName);

}
