package vn.ohana.controllers.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.post.PostService;

import java.util.Set;

@RestController
@RequestMapping("api/posts")
@CrossOrigin("*")
public class PostAPI {
    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        return new ResponseEntity<>(postService.findAll(pageable), HttpStatus.OK);
    }

    @PatchMapping ("/approveAll")
    public ResponseEntity<?> approveAll(@RequestBody Set<Long> ids) {
        postService.modifyStatusByIds(ids, "PUBLISHED");
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
