package vn.ohana.location.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationParam {
    private String line1;

    private Long wardId;
    private String wardName;

    private Long districtId;
    private String districtName;

    private Long provinceId;
    private String provinceName;
}