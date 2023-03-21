package vn.ohana.utility;

import org.springframework.stereotype.Component;
import vn.ohana.Utility;
import vn.ohana.utility.dto.UtilityResult;

@Component
public class UtilityMapper {

    public UtilityResult toDTO(Utility utility) {
        UtilityResult utilityResult = new UtilityResult();
        utilityResult.setId(utility.getId());
        utilityResult.setName(utility.getName());
        utilityResult.setIcon(utility.getIcon());
        utilityResult.setHidden(utility.getStatus());
        utilityResult.setPriority(utility.getPriority());
        return utilityResult;
    }


}
