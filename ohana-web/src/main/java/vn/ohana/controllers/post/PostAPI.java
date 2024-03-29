package vn.ohana.controllers.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.ohana.entities.Location;
import vn.ohana.entities.StatusPost;
import vn.ohana.post.PostService;
import vn.ohana.post.dto.PostCreateParam;
import vn.ohana.post.dto.PostFilterParam;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;
import vn.ohana.user.dto.UserUpdateParam;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("api/posts")
public class PostAPI {
    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "size",defaultValue = "10") int size, @RequestParam(name = "sort",defaultValue ="DESC" )Sort.Direction direction) {

        return new ResponseEntity<>(postService.findAll(PageRequest.of(page,size,Sort.by(direction,"createdAt"))), HttpStatus.OK);
    }

    @PostMapping("/{userId}/user")
    public ResponseEntity<?> findAllByUserId(@PathVariable Long userId, @RequestBody UserUpdateParam user, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return new ResponseEntity<>(postService.findAllByUser(user,PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("/{pId}")
    public ResponseEntity<?> findById(@PathVariable Long pId) {
        return new ResponseEntity<>(postService.getById(pId), HttpStatus.OK);
    }



    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping ("/{status}/status")
    public ResponseEntity<?> updateStatusAll(@PathVariable String status, @RequestBody Set<Long> ids) {
        return new ResponseEntity<>(postService.modifyStatusByIds(ids, status),HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody PostFilterParam filter, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
            return new ResponseEntity<>(postService.filter(filter, PageRequest.of(page, size,Sort.by(Sort.Direction.DESC,"createdAt"))), HttpStatus.OK);
    }

    @PostMapping("/filter/published")
    public ResponseEntity<?> filterPublished(@RequestBody PostFilterParam filter, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return new ResponseEntity<>(postService.filterPublishedPosts(filter, PageRequest.of(page, size)), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PatchMapping ("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id,@RequestBody PostUpdateParam postUpdateParam) {
        return new ResponseEntity<>( postService.updateById(postUpdateParam), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PatchMapping ("/status/{id}")
    public ResponseEntity<?> updateStatusById(@PathVariable Long id,@RequestBody PostUpdateParam postUpdateParam) {
        return new ResponseEntity<>( postService.updateStatusById(postUpdateParam), HttpStatus.OK);
    }

    @PostMapping("/trendPost")
    public ResponseEntity<?> getPostsNew(@RequestBody PostFilterParam filterParam) {
        Location location = filterParam.getLocationFilter();
        Long proviceId = location.getProvinceId();
        return new ResponseEntity<>(postService.getTop10PostsNew(proviceId),HttpStatus.OK);
    }

    @PostMapping("/postsNew")
    public ResponseEntity<?> doPostsNew(@RequestBody PostCreateParam postCreateParam) {
        return new ResponseEntity<>(postService.save(postCreateParam),HttpStatus.OK);
    }

    @GetMapping("/{uId}/user/{status}/status")
    public ResponseEntity<?> listPublished(@PathVariable Long uId, @PathVariable String status, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) throws IOException {
        StatusPost statusPost = StatusPost.parseStatusPosts(status);
        Page<PostResult> postResults = postService.findAllByStatusAndUser(statusPost, uId, PageRequest.of(page, size));
        return new ResponseEntity<>(postResults, HttpStatus.OK);
    }
}
