package vn.ohana.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ohana.entities.Gender;

import javax.persistence.Column;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentHouseResult {
    private Long id;
    private String name;
    private BigDecimal roomPrice;
    private BigDecimal deposit;
    private BigDecimal electricityPrice;
    private BigDecimal waterPrice;
    private BigDecimal wifiPrice;
    private Long capacity;
    private Long area;
    private Gender gender;
    private boolean status;

    @Override
    public String toString() {
        return "RentHouseParam{" +
                "name='" + name + '\'' +
                ", roomPrice=" + roomPrice +
                ", capacity=" + capacity +
                ", area=" + area +
                ", gender='" + gender + '\'' +
                '}';
    }
}