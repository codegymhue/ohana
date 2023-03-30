package vn.ohana.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.Post;
import vn.ohana.post.dto.PostResult;
import vn.ohana.utility.UtilityService;
import vn.ohana.utility.dto.UtilityResult;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CPostRepository cpostRepository;
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


}
