package vn.ohana.report;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.UserStatus;
import vn.ohana.post.dto.PostResult;
import vn.ohana.report.dto.DateReportResult;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface ReportService {

    Long countCategory();

    Long countUser();

    Long countUtility();

    Long countPost();



    Long countUserByStatus(UserStatus status);

    Long countPostByStatus(StatusPost status);

    Page<PostResult> countTenNearestPendingPosts();

    Map<String,Long> getUserAnalysis();

    Map<String,Long> getPostAnalysis();
    List<DateReportResult> countPostByMonthBetweenDate(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
    Map<String,List<DateReportResult>> countPostsAndUsersByMonthBetweenDate(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
}
