package vn.ohana.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.utility.UtilityRepository;


@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;


    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CPostRepository cpostRepository;
    @Autowired
    private UtilityRepository utilityRepository;


}
