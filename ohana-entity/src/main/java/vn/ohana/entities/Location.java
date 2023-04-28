package vn.ohana.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Location implements Serializable {
    private String line1;

    private Long wardId;
    private String wardName;

    private Long districtId;
    private String districtName;

    private Long provinceId;
    private String provinceName;

    private String line1Unsigned;
    private String wardUnsignedName;
    private String districtUnsignedName;
    private String provinceUnsignedName;

    @JsonProperty("line1Unsigned")
    public String getLine1Unsigned() {
        return StringUtils.stripAccents(line1).toLowerCase();
    }

    @JsonProperty("wardUnsignedName")
    public String getWardUnsignedName() {
        return StringUtils.stripAccents(wardName).toLowerCase();
    }


    @JsonProperty("provinceUnsignedName")
    public String getProvinceUnsignedName() {
        return StringUtils.stripAccents(provinceName).toLowerCase();
    }

    @JsonProperty("districtUnsignedName")
    public String getDistrictUnsignedName() {
        return StringUtils.stripAccents(districtName).toLowerCase();
    }


}