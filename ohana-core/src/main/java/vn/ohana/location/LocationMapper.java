package vn.ohana.location;

import org.springframework.stereotype.Component;
import vn.ohana.entities.Location;
import vn.ohana.location.dto.LocationParam;
import vn.ohana.location.dto.LocationResult;
import vn.rananu.shared.mappers.BaseMapper;

@Component
public class LocationMapper extends BaseMapper<LocationResult, Location, LocationParam> {

}
