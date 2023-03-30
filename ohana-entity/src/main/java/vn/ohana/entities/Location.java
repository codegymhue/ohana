package vn.ohana.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Location implements Serializable {
    private Long provinceId;
    private String provinceName;
    private String provinceUnsignedName;
    private Long districtId;
    private String districtName;
    private String districtUnsignedName;
    private Long wardId;
    private String wardName;
    private String wardUnsignedName;
    private String line1;
    private String line1Unsigned;
}