package vn.ohana.report.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class DateReportParam {
    private Date startDate = new Date();
    private Date endDate = new Date();
}
