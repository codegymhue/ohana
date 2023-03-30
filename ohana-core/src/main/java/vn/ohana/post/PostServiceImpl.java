package vn.ohana.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.Post;
import vn.ohana.post.dto.PostCreateParam;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;
import vn.ohana.utility.UtilityRepository;

import java.io.IOException;
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
    private UtilityRepository utilityRepository;


    @Override
    @Transactional(readOnly = true)
    public List<PostResult> findAll() {
        List<Post> entities = postRepository.findAll();
       return List<PostResult> dtoList = postMapper.toDTOList(entities);
    }


}
