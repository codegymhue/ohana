package vn.ohana.utility;

import vn.ohana.entities.StatusUtility;
import vn.ohana.utility.dto.UpdateUtilityParam;
import vn.ohana.utility.dto.UtilityResult;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UtilityService {
    List<UtilityResult> findAll();

    Optional<UtilityResult> findById(Long id);



    List<UtilityResult> findAllByStatus(StatusUtility status);



    void deleteById(Long id);

    UtilityResult update(UpdateUtilityParam param);
}
