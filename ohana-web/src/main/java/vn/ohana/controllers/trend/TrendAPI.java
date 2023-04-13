package vn.ohana.controllers.trend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ohana.trend.TrendService;

@RestController
@RequestMapping("api/trends")
public class TrendAPI {
    @Autowired
    TrendService trendService;


}
