package vn.ohana.location.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationParam {
    private Long provinceId;
    private String provinceName;
    private Long districtId;
    private String districtName;
    private Long wardId;
    private String wardName;
    private String line1;
}