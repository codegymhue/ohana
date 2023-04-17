package vn.ohana.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import vn.ohana.entities.Location;
import vn.ohana.entities.Post;
import vn.ohana.location.dto.LocationParam;
import vn.ohana.location.dto.LocationResult;
import vn.ohana.post.dto.PostCreateParam;
import vn.rananu.shared.mappers.BaseMapper;

@Component
public class LocationMapper extends BaseMapper<LocationResult, Location, LocationParam> {
    public String toLocation(LocationParam locationParam){
        ObjectMapper om = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = om.writeValueAsString(locationParam);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(jsonString);
        return jsonString;
    }
}
