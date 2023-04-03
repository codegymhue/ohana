package vn.ohana.controllers.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.post.PostService;

@RestController
@RequestMapping("api/posts")
public class PostAPI {
    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        return new ResponseEntity<>(postService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id) {
        postService.approve(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping({"/{id}/unapproved"})
    public ResponseEntity<?> unApprove(@PathVariable Long id) {
        postService.unApprove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
