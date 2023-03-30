package vn.ohana.post;

import vn.ohana.entities.Post;
import vn.ohana.post.dto.PostResult;

import java.util.List;


public interface PostService {

    List<PostResult> findAll();


}
