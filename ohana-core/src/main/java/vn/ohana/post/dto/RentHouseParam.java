package vn.ohana.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ohana.entities.Gender;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentHouseParam {
    private BigDecimal roomPrice;
    private Long capacity;
    private Long area;
    private Gender gender;

    private BigDecimal electricityPrice;

    private BigDecimal waterPrice;

    private BigDecimal wifiPrice;

    @Override
    public String toString() {
        return "RentHouseParam{" +
                ", price=" + roomPrice +
                ", capacity=" + capacity +
                ", area=" + area +
                ", gender='" + gender + '\'' +
                '}';
    }
}