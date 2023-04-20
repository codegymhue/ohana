package vn.ohana.controllers.report;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ohana.category.CategoryService;
import vn.ohana.report.ReportService;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin("*")
public class ReportAPI {

    @Autowired
    ReportService reportService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/countCategory")
    public ResponseEntity<?> countAllCategory() {

        return new ResponseEntity<>(reportService.count(), HttpStatus.OK);
    }

}
