package vn.ohana.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.Post;
import vn.ohana.post.dto.PostResult;
import vn.ohana.utility.UtilityRepository;
import vn.ohana.utility.UtilityService;

import java.awt.print.Pageable;
import java.util.List;


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
    public List<PostResult> findAll(Pageable pageable) {
        List<Post> entities = postRepository.findAll(pageable);
        List<PostResult> dtoList = postMapper.toDTOList(entities);
        utilityService.fin
    }


}
