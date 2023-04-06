package vn.ohana.controllers.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.entities.User;
import vn.ohana.post.PostService;
import vn.ohana.post.dto.PostFilterParam;
import vn.ohana.post.dto.PostUpdateParam;

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

    @PostMapping("/{userId}/user")
    public ResponseEntity<?> findAllByUserId(@PathVariable Long userId, @RequestBody User user,@RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return new ResponseEntity<>(postService.findAllByUser(user,PageRequest.of(page, size)), HttpStatus.OK);
    }

    @PatchMapping ("/approveAllPosts")
    public ResponseEntity<?> approveAll(@RequestBody Set<Long> ids) {
        return new ResponseEntity<>(postService.modifyStatusPostByIds(ids, "PUBLISHED") ,HttpStatus.OK);
    }

    @PatchMapping ("/unApproveAllPosts")
    public ResponseEntity<?> unApproveAll(@RequestBody Set<Long> ids) {
        return new ResponseEntity<>(postService.modifyStatusPostByIds(ids, "REFUSED") ,HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody PostFilterParam filter, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return new ResponseEntity<>(postService.filter(filter, PageRequest.of(page, size)), HttpStatus.OK);
    }

    @PatchMapping ("/edit")
    public ResponseEntity<?> edit(@ModelAttribute PostUpdateParam postUpdateParam) {
        postService.postEdit(postUpdateParam);
        return new ResponseEntity<>("Cập nhật bài viết thành công", HttpStatus.OK);
    }


}
