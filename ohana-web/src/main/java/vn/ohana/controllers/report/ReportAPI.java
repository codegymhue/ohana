package vn.ohana.controllers.report;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.category.CategoryService;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.UserStatus;
import vn.ohana.report.ReportService;

import java.util.Set;

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
        return new ResponseEntity<>(reportService.countUserByStatus(status),HttpStatus.OK);
    }

    @GetMapping("/{status}/postStatus")
    public ResponseEntity<?> countAllPostByStatus(@PathVariable StatusPost status) {
        return new ResponseEntity<>(reportService.countPostByStatus(status),HttpStatus.OK);
    }

    @GetMapping("/countUtility")
    public ResponseEntity<?> countAllUtility() {

        return new ResponseEntity<>(reportService.countUtility(), HttpStatus.OK);
    }

    @GetMapping("/countPost")
    public ResponseEntity<?> countAllPost() {

        return new ResponseEntity<>(reportService.countPost(), HttpStatus.OK);
    }

}
