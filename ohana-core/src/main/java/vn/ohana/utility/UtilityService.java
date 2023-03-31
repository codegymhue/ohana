package vn.ohana.utility;

import vn.ohana.entities.StatusUtility;
import vn.ohana.entities.Utility;
import vn.ohana.utility.dto.UpdateUtilityParam;
import vn.ohana.utility.dto.UtilityResult;

import java.util.*;

public interface UtilityService {
    List<UtilityResult> findAllByIds(Set<Integer> ids);

    Utility findById(Integer id);

    UtilityResult getById(Integer id);


    List<UtilityResult> findAllByStatus(StatusUtility status);

    void deleteById(Integer id);

    UtilityResult update(UpdateUtilityParam param);

    List<UtilityResult> findAll();
}
