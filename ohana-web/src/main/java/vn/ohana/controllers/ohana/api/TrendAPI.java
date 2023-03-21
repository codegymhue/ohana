package vn.ohana.controllers.ohana.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.ohana.trend.TrendService;
import vn.tg.ohana.repository.model.Trend;

import java.util.List;

@RestController
@RequestMapping("/api/trends")
public class TrendAPI {
    @Autowired
    TrendService trendService;

    @GetMapping()
    public ResponseEntity<?> getCategories() {
        List<Trend> trends = trendService.getAllTrend();
        return new ResponseEntity<>(trends, HttpStatus.OK);
    }
}
