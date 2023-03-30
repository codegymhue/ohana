package vn.ohana.location.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationResult {
    private Long wardId;
    private String wardName;
    private String wardUnsignedName;

    private Long districtId;
    private String districtName;
    private String districtUnsignedName;

    private Long provinceId;
    private String provinceName;
    private String provinceUnsignedName;

    private String line1;
    private String line1Unsigned;
}
