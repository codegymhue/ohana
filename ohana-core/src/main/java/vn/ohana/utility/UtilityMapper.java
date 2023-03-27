package vn.ohana.utility;

import org.springframework.stereotype.Component;
import vn.ohana.entities.Utility;
import vn.ohana.utility.dto.BaseUtility;
import vn.ohana.utility.dto.UtilityResult;
import vn.rananu.shared.mappers.BaseMapper;

@Component
public class UtilityMapper extends BaseMapper<UtilityResult,Utility, BaseUtility> {

}
