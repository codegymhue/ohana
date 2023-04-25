package vn.ohana.controllers.report;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.UserStatus;
import vn.ohana.report.dto.DateReportParam;
import vn.ohana.report.ReportService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/reports")
public class ReportAPI {

    @Autowired
    ReportService reportService;


    @GetMapping("/countCategory")
    public ResponseEntity<?> countAllCategory() {

        return new ResponseEntity<>(reportService.countCategory(), HttpStatus.OK);
    }

    @GetMapping("/countUser")
    public ResponseEntity<?> countAllUser() {

        return new ResponseEntity<>(reportService.countUser(), HttpStatus.OK);
    }

    @GetMapping("/{status}/userStatus")
    public ResponseEntity<?> countAllUserByStatus(@PathVariable UserStatus status) {
        return new ResponseEntity<>(reportService.countUserByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/{status}/postStatus")
    public ResponseEntity<?> countAllPostByStatus(@PathVariable StatusPost status) {
        return new ResponseEntity<>(reportService.countPostByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/countUtility")
    public ResponseEntity<?> countAllUtility() {

        return new ResponseEntity<>(reportService.countUtility(), HttpStatus.OK);
    }

    @GetMapping("/countPost")
    public ResponseEntity<?> countAllPost() {

        return new ResponseEntity<>(reportService.countPost(), HttpStatus.OK);
    }

    @GetMapping("/analyze/users")
    public ResponseEntity<?> getUsersAnalysis() {

        return new ResponseEntity<>(reportService.getUserAnalysis(), HttpStatus.OK);
    }

    @GetMapping("/analyze/posts")
    public ResponseEntity<?> getPostsAnalysis() {

        return new ResponseEntity<>(reportService.getPostAnalysis(), HttpStatus.OK);
    }

    @GetMapping("/ten/pending_posts")
    public ResponseEntity<?> getTenNearestPendingPosts() {
        return new ResponseEntity<>(reportService.countTenNearestPendingPosts(), HttpStatus.OK);
    }

    @PostMapping("/count/month/posts")
    public ResponseEntity<?> countByMonthBetween(@RequestBody DateReportParam dateReportParam) {
        return new ResponseEntity<>(reportService.countPostByMonthBetweenDate(dateReportParam.getStartDate().toInstant(), dateReportParam.getEndDate().toInstant()), HttpStatus.OK);
    }

    @PostMapping("/count/monthly/unp")
    public ResponseEntity<?> countUserAndPostByMonthBetween(@RequestBody DateReportParam dateReportParam) {
        Instant startDate = dateReportParam.getStartDate().toInstant();
        Instant endDate = dateReportParam.getEndDate().toInstant();
        return new ResponseEntity<>(reportService.countPostsAndUsersByMonthBetweenDate(startDate, endDate), HttpStatus.OK);
    }

    @PostMapping("/analyze/monthly")
    public ResponseEntity<?> getDataByMonth(@RequestBody String monthAndYear) throws ParseException {
        return new ResponseEntity<>(reportService.getDataByMonth(monthAndYear),HttpStatus.OK);
    }
}
