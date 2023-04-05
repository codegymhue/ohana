package vn.ohana.controllers.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.post.PostService;
import vn.ohana.post.dto.PostUpdateParam;

import java.util.Set;

@RestController
@RequestMapping("api/posts")
public class PostAPI {
    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        return new ResponseEntity<>(postService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAllByUserId(@RequestParam Long userId) {
        return new ResponseEntity<>(postService.findAllByUserId(userId), HttpStatus.OK);
    }

    @PatchMapping ("/approveAllPosts")
    public ResponseEntity<?> approveAll(@RequestBody Set<Long> ids) {
        return new ResponseEntity<>(postService.modifyStatusPostByIds(ids,"PUBLISHED") ,HttpStatus.OK);
    }

    @PatchMapping ("/unApproveAllPosts")
    public ResponseEntity<?> unApproveAll(@RequestBody Set<Long> ids) {
        return new ResponseEntity<>(postService.modifyStatusPostByIds(ids,"REFUSED") ,HttpStatus.OK);
    }

    @PatchMapping ("/edit")
    public ResponseEntity<?> edit(@ModelAttribute PostUpdateParam postUpdateParam) {
        postService.postEdit(postUpdateParam);
        return new ResponseEntity<>("Cập nhật bài viết thành công", HttpStatus.OK);
    }


}
