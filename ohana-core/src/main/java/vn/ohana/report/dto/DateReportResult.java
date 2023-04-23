package vn.ohana.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;


@Getter
@Setter
@AllArgsConstructor
public class DateReportResult {
    BigInteger count;
    String date;
}
