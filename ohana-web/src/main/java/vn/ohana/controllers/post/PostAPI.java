package vn.ohana.controllers.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ohana.post.PostService;

@RestController
@RequestMapping("api/posts")
public class PostAPI {
    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(postService.findAll(),HttpStatus.OK);
    }
}
