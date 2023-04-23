package vn.ohana.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.category.CategoryRepository;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.UserStatus;
import vn.ohana.post.PostService;
import vn.ohana.post.dto.PostResult;
import vn.ohana.report.dto.DateReportResult;
import vn.ohana.user.UserService;
import vn.ohana.utility.UtilityRepository;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserService userService;

    @Autowired
    UtilityRepository utilityRepository;


    @Autowired
    PostService postService;


    @Override
    public Long countCategory() {
        return categoryRepository.count();
    }

    @Override
    public Long countUser() {
        return userService.count();
    }

    @Override
    public Long countUtility() {
        return utilityRepository.count();
    }

    @Override
    public Long countPost() {
        return postService.count();
    }

    @Override
    public Long countUserByStatus(UserStatus status) {
        return userService.countUsersByStatus(status);
    }

    @Override
    public Long countPostByStatus(StatusPost status) {
        return postService.countPostByStatus(status);
    }

    @Override
    public Page<PostResult> countTenNearestPendingPosts() {
        return postService.findAllByStatus(StatusPost.PENDING_REVIEW, PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"createdAt")));
    }

    @Override
    public Map<String, Long> getUserAnalysis() {
        Map<String,Long> analysis =new HashMap<>();

        Long allUsers = userService.count();
        analysis.put("ALL", allUsers);
        Long numberActivated = userService.countUsersByStatus(UserStatus.ACTIVATED);
        analysis.put(UserStatus.ACTIVATED.getValue(), numberActivated);
        Long numberDeactivated = userService.countUsersByStatus(UserStatus.DEACTIVATED);
        analysis.put(UserStatus.DEACTIVATED.getValue(), numberDeactivated);
        Long numberConfirmEmail = userService.countUsersByStatus(UserStatus.CONFIRM_EMAIL);
        analysis.put(UserStatus.CONFIRM_EMAIL.getValue(), numberConfirmEmail);

        return analysis;
    }

    @Override
    public Map<String, Long> getPostAnalysis() {
        Map<String,Long> analysis =new HashMap<>();
        Long allPosts = postService.count();
        analysis.put("ALL", allPosts);
        Long numberPublished = postService.countPostByStatus(StatusPost.PUBLISHED);
        analysis.put(StatusPost.PUBLISHED.getValue(), numberPublished);
        Long numberPending = postService.countPostByStatus(StatusPost.PENDING_REVIEW);
        analysis.put(StatusPost.PENDING_REVIEW.getValue(), numberPending);
        Long numberRefused = postService.countPostByStatus(StatusPost.REFUSED);
        analysis.put(StatusPost.REFUSED.getValue(), numberRefused);
        Long numberDeleted = postService.countPostByStatus(StatusPost.DELETED);
        analysis.put(StatusPost.DELETED.getValue(), numberDeleted);

        return analysis;
    }

    @Override
    public List<DateReportResult> countPostByMonthBetweenDate(Instant startDate, Instant endDate) {
        return toDaReportResult( postService.countByMonthBetweenDate(startDate, endDate));
    }

    @Override
    public Map<String,List<DateReportResult>> countPostsAndUsersByMonthBetweenDate(Instant startDate, Instant endDate) {
        Map<String,List<DateReportResult>> stringListMap =new HashMap<>();
        List<DateReportResult> postReportResults = toDaReportResult(postService.countByMonthBetweenDate(startDate, endDate));
        List<DateReportResult> userReportResults = toDaReportResult(userService.countByMonthBetweenDate(startDate, endDate));
        stringListMap.put("postMonthlyCount", postReportResults);
        stringListMap.put("userMonthlyCount", userReportResults);
        return stringListMap;
    }

    private List<DateReportResult> toDaReportResult(List<Object> objects) {
        List <DateReportResult> reportResults = new ArrayList<>();
        objects.forEach(obj->{
            Object[] newObj = (Object[]) obj;
            reportResults.add(new DateReportResult((BigInteger) newObj[0],(String) newObj[1]));
        });
        return reportResults;
    }
}
