package vn.ohana.post.dto;

import org.springframework.stereotype.Component;
import vn.ohana.entities.Gender;
import vn.ohana.entities.Post;
import vn.ohana.entities.RentHouse;
import vn.rananu.shared.mappers.BaseMapper;

@Component
public class RentHouseMapper extends BaseMapper<PostResult, Post, PostUpdateParam> {
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

    public RentHouse toRentHouseDTO(RentHouseParam rentHouseParam) {
        RentHouse rentHouse = new RentHouse();
        rentHouse.setPrice(rentHouseParam.getPrice());
        rentHouse.setCapacity(rentHouseParam.getCapacity());
        rentHouse.setArea(rentHouseParam.getArea());
        rentHouse.setGender(rentHouse.getGender());
        return rentHouse;
    }

    public RentHouse toRentHouse(RentHouseParam rentHouseParam) {
        return modelMapper.map(rentHouseParam, RentHouse.class);
    }

}
