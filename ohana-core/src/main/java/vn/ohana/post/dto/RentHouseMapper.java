package vn.ohana.post.dto;

import org.springframework.stereotype.Component;
import vn.ohana.entities.Gender;
import vn.ohana.entities.RentHouse;
import vn.ohana.post.dto.RentHouseParam;
import vn.ohana.post.dto.RentHouseResult;

@Component
public class RentHouseMapper {
    public RentHouseResult toDTO(RentHouse rentHouse) {
        RentHouseResult result = new RentHouseResult();
        result.setId(rentHouse.getId());
        result.setCapacity(rentHouse.getCapacity());
        result.setGender(rentHouse.getGender());
        result.setPrice(rentHouse.getPrice());
        result.setArea(rentHouse.getArea());
        result.setStatus(rentHouse.isStatus());
        return result;
    }

    public RentHouse toRentHouse(RentHouseParam rentHouseParam) {
        RentHouse rentHouse = new RentHouse();

        rentHouse.setPrice(rentHouseParam.getPrice());
        rentHouse.setCapacity(rentHouseParam.getCapacity());
        rentHouse.setArea(rentHouseParam.getArea());
        rentHouse.setGender(new Gender(rentHouseParam.getGenderId()));
        return rentHouse;
    }

}
