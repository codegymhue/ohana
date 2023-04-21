package vn.ohana.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ohana.category.CategoryRepository;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.UserStatus;
import vn.ohana.post.PostRepository;
import vn.ohana.user.UserRepository;
import vn.ohana.utility.UtilityRepository;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilityRepository utilityRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public Long countCategory() {
        return categoryRepository.count();
    }

    @Override
    public Long countUser() {
        return userRepository.count();
    }

    @Override
    public Long countUtility() {
        return utilityRepository.count();
    }

    @Override
    public Long countPost() {
        return postRepository.count();
    }

    @Override
    public Long countUserByStatus(UserStatus status) {
        return userRepository.countUsersByStatus(status);
    }

    @Override
    public Long countPostByStatus(StatusPost status) {
        return postRepository.countPostByStatus(status);
    }
}
