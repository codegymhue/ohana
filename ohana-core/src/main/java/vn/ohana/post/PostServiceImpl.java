package vn.ohana.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.Post;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.User;
import vn.ohana.entities.UserStatus;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;
import vn.ohana.utility.UtilityService;
import vn.ohana.utility.dto.UtilityResult;
import vn.rananu.shared.exceptions.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UtilityService utilityService;

    @Override
    @Transactional(readOnly = true)
    public Page<PostResult> findAll(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        List<Post> entities = page.getContent();

        Set<Integer> utilityIds = entities.stream().map(Post::getUtilities)
                .flatMap(Set::stream).collect(Collectors.toSet());

        List<UtilityResult> utilities = utilityService.findAllByIds(utilityIds);

        return page.map(entity -> {
            PostResult dto = postMapper.toDTO(entity);
            List<UtilityResult> newUtilities = utilities
                    .stream()
                    .filter(utility ->
                            entity.getUtilities()
                                    .stream()
                                    .anyMatch(id -> utility.getId().equals(id)))
                    .collect(Collectors.toList());
            dto.setUtilities(newUtilities);
            return dto;
        });
    }


    @Override
    public Map<Long, String> modifyStatusPostByIds(Set<Long> ids, String published) {
        StatusPost statusPost = StatusPost.parseStatusPosts(published);
        Map<Long, String> result = new HashMap<>();
        Iterable<Post> entities = postRepository.findAllById(ids);
        entities.forEach(entity -> {
            entity.setStatus(statusPost);
            result.put(entity.getId(), "Posts are approved successful");
        });
        return result;
    }

    @Override
    public Map<Long, String> notModifyStatusPostByIds(Set<Long> ids, String refused) {
        StatusPost statusPost = StatusPost.parseStatusPosts(refused);
        Map<Long, String> result = new HashMap<>();
        Iterable<Post> entities = postRepository.findAllById(ids);
        entities.forEach(entity -> {
            entity.setStatus(statusPost);
            result.put(entity.getId(), "Posts are approved failed");
        });
        return result;
    }

    @Override
    public void postEdit(PostUpdateParam postUpdateParam) {
        Post entity=  findById(postUpdateParam.getId());
    }


    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("post.exception.notFound"));
    }


}
