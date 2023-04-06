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
import vn.ohana.user.dto.UserFilterParam;

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
    @GetMapping("/{pId}")
    public ResponseEntity<?> findById(@PathVariable Long pId) {
        return new ResponseEntity<>(postService.getById(pId), HttpStatus.OK);
    }
    @PatchMapping ("/{userId}/users")
    public ResponseEntity<?> approveAll(@RequestBody Set<Long> ids) {
        postService.modifyStatusByIds(ids, "PUBLISHED");
        return new ResponseEntity<>(HttpStatus.OK);
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
