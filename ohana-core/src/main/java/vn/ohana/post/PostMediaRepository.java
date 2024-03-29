package vn.ohana.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.PostMedia;

import java.util.List;

@Repository
public interface PostMediaRepository extends JpaRepository<PostMedia, String> {

    List<PostMedia> findByPostId(Long postId);

    List<PostMedia> findAllByIdNotAndPostId(String thumbnailId, Long postId);

    PostMedia findByFileUrl(String fileUrl);

    boolean existsById(String postMediaId);

    void removeAllByPost_Id(Long postId);
}
