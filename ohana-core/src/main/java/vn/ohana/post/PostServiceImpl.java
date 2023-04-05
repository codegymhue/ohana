package vn.ohana.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.Post;
import vn.ohana.entities.StatusPost;
import vn.ohana.post.dto.PostFilterParam;
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

    @Override
    public Map<Long, String> modifyStatusByIds(Set<Long> ids, String published) {
        return null;
    }

    @Autowired
    private PostMapper postMapper;

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
    public Object findAllByUserId(Long userId) {
        return null;
    }


    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("post.exception.notFound"));
    }

    @Override
    public Page<PostResult> filter(PostFilterParam filter, Pageable pageable) {
        Page<Post> page = postFilterRepository.findAllByFilters(filter, pageable);
        return postMapper.toDtoPage(page);
    }

    private Page<PostResult> toDtoPage(Page<Post> page) {
        return page.map(entity -> postMapper.toDTO(entity));
    }


}
