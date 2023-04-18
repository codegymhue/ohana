package vn.ohana.controllers.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import vn.ohana.entities.Post;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.User;
import vn.ohana.location.dto.DataSearchResult;
import vn.ohana.post.PostRepository;
import vn.ohana.post.PostService;
import vn.ohana.post.dto.PostCreateParam;
import vn.ohana.post.dto.PostFilterParam;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;
import vn.ohana.user.dto.LoginResult;
import vn.ohana.user.dto.UserFilterParam;
import vn.ohana.user.dto.UserUpdateParam;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static vn.ohana.config.MailConfig.MY_EMAIL;

@RestController
@RequestMapping("api/posts")
@CrossOrigin("*")
public class PostAPI {
    @Autowired
    PostService postService;

    @Autowired
    public JavaMailSender emailSender;

    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        return new ResponseEntity<>(postService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping("/{userId}/user")
    public ResponseEntity<?> findAllByUserId(@PathVariable Long userId, @RequestBody UserUpdateParam user, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return new ResponseEntity<>(postService.findAllByUser(user,PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("/{pId}")
    public ResponseEntity<?> findById(@PathVariable Long pId) {
        return new ResponseEntity<>(postService.getById(pId), HttpStatus.OK);
    }

    @GetMapping("/{pId}/email")
    public ResponseEntity<?> findEmailById(@PathVariable Long pId) {
        return new ResponseEntity<>(postService.getById(pId).getUser().getEmail(), HttpStatus.OK);
    }

//    @PostMapping("/{pId}/email")
//    public ResponseEntity<?> doSendEmailById(@PathVariable Long pId) {
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        String email = postService.getById(pId).getUser().getEmail();
//
//        message.setFrom(MY_EMAIL);
//        message.setTo(email);
//        message.setSubject("Email thông báo kiểm duyệt bài đăng");
//        message.setText("Bài viết của bạn đã bị khóa!");
//
//        this.emailSender.send(message);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PatchMapping ("/{status}/status")
    public ResponseEntity<?> updateStatusAll(@PathVariable String status, @RequestBody Set<Long> ids) {
        return new ResponseEntity<>(postService.modifyStatusByIds(ids, status),HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody PostFilterParam filter, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
            return new ResponseEntity<>(postService.filter(filter, PageRequest.of(page, size)), HttpStatus.OK);
    }

    @PostMapping("/filter/published")
    public ResponseEntity<?> filterPublished(@RequestBody PostFilterParam filter, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return new ResponseEntity<>(postService.filterPublishedPosts(filter, PageRequest.of(page, size)), HttpStatus.OK);
    }

    @PatchMapping ("/edit")
    public ResponseEntity<?> edit(@ModelAttribute PostUpdateParam postUpdateParam) {
        postService.postEdit(postUpdateParam);
        return new ResponseEntity<>("Cập nhật bài viết thành công", HttpStatus.OK);
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id,@RequestBody PostUpdateParam postUpdateParam) {
        SimpleMailMessage message = new SimpleMailMessage();

        String email = postService.getById(id).getUser().getEmail();

        message.setFrom(MY_EMAIL);
        message.setTo(email);
        message.setSubject("Email thông báo kiểm duyệt bài đăng");
        if (postService.updateStatusById(postUpdateParam).getStatus() == StatusPost.PUBLISHED) {

            message.setText("Bài viết của bạn đã được đăng!");
        }
        if (postService.updateStatusById(postUpdateParam).getStatus() == StatusPost.REFUSED) {

            message.setText("Bài viết của bạn đã bị thu hồi!");
        }

        this.emailSender.send(message);
        return new ResponseEntity<>( postService.updateStatusById(postUpdateParam), HttpStatus.OK);
    }

    @GetMapping("/postsNew")
    public ResponseEntity<?> getPostsNew() {
        return new ResponseEntity<>(postService.getTop10PostsNew(),HttpStatus.OK);
    }

    @PostMapping("/postsNew")
    public ResponseEntity<?> doPostsNew(@RequestBody PostCreateParam postCreateParam) {
        ;
        return new ResponseEntity<>(postService.save(postCreateParam),HttpStatus.OK);
    }

    @GetMapping("/{uId}/user/{status}/status")
    public ResponseEntity<?> listPublished(@PathVariable Long uId, @PathVariable String status, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) throws IOException {
        StatusPost statusPost = StatusPost.parseStatusPosts(status);
        Page<PostResult> postResults = postService.findAllByStatusAndUser(statusPost, uId, PageRequest.of(page, size));
        return new ResponseEntity<>(postResults, HttpStatus.OK);
    }
}
