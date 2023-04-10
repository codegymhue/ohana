package vn.ohana.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.Post;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.User;
import vn.ohana.post.dto.PostFilterParam;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;
import vn.ohana.user.UserMapper;
import vn.ohana.user.UserRepository;
import vn.ohana.user.dto.UserUpdateParam;
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
    private UserMapper userMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private PostFilterRepository postFilterRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<PostResult> findAll(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        return insertUtilityResultList(page);
    }

    @Override
    public Map<Long, String> modifyStatusByIds(Set<Long> ids, String published) {
        return null;
    }

    public Page<PostResult> insertUtilityResultList(Page<Post> page) {
        List<Post> entities = page.getContent();


        Set<Integer> utilityIds = entities.stream().map(Post::getUtilities)
                .flatMap(Set::stream).collect(Collectors.toSet());


        return page.map(entity -> addPostResultUtilities(entity,utilityIds));
    }


    public Map<Long, String> modifyStatusPostByIds(Set<Long> ids, String statusPost) {
        StatusPost status = StatusPost.parseStatusPosts(statusPost);
        Map<Long, String> result = new HashMap<>();
        Iterable<Post> entities = postRepository.findAllById(ids);
        entities.forEach(entity -> {
            entity.setStatus(status);
            result.put(entity.getId(), "successful");
        });

        List<Long> entityIds = StreamSupport.stream(entities.spliterator(), false).map(Post::getId).collect(Collectors.toList());
        ids.forEach(id -> {
            if (!entityIds.contains(id))
                result.put(id, "failed");
        });
        return result;
    }


    @Override
    public void postEdit(PostUpdateParam postUpdateParam) {
        Post entity=  findById(postUpdateParam.getId());
    }

    @Override
    public Page<PostResult> findAllByUser(UserUpdateParam user, Pageable pageable) {

        Page<Post> post = postRepository.findByUser(userMapper.toEntity(user),pageable);

        return insertUtilityResultList(post);
    }

    @Override
    public PostResult getById(Long pId) {

        Post post = findById(pId);
        return addPostResultUtilities(post, post.getUtilities());
    }

    @Override
    @Transactional
    public PostResult updateStatusById(PostUpdateParam postUpdateParam) {
        Post post = findById(postUpdateParam.getId());
        postMapper.transferFields(postUpdateParam,post,true);
        return postMapper.toDTO(post);
    }


    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("post.exception.notFound"));
    }

    @Override
    public Page<PostResult> filter(PostFilterParam filter, Pageable pageable) {
        Page<Post> page = postFilterRepository.findAllByFilters(filter, pageable);
         return insertUtilityResultList(page);
    }

    private Page<PostResult> toDtoPage(Page<Post> page) {
        return page.map(entity -> postMapper.toDTO(entity));
    }

    private PostResult addPostResultUtilities(Post entity, Set<Integer> utilityIds) {
        List<UtilityResult> utilities = utilityService.findAllByIds(utilityIds);
        PostResult dto= postMapper.toDTO(entity);
        List<UtilityResult> newUtilities = utilities
                .stream()
                .filter(utility ->
                        entity.getUtilities()
                                .stream()
                                .anyMatch(id -> utility.getId().equals(id)))
                .collect(Collectors.toList());
        dto.setUtilities(newUtilities);
        return dto;
    }
}
