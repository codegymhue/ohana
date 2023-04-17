package vn.ohana.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

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






}