package vn.ohana.utility;

import vn.ohana.entities.StatusUtility;
import vn.ohana.entities.Utility;
import vn.ohana.utility.dto.UpdateUtilityParam;
import vn.ohana.utility.dto.UtilityResult;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UtilityService {
    List<UtilityResult> findAllByIds(Set<Long> ids);

    Utility findById(Long id);

    UtilityResult getById(Long id);


    List<UtilityResult> findAllByStatus(StatusUtility status);

    void deleteById(Long id);

    UtilityResult update(UpdateUtilityParam param);
}
