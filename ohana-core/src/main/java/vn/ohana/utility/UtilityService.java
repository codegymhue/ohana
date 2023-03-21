package vn.ohana.utility;

import vn.ohana.StatusUtility;
import vn.ohana.Utility;
import vn.ohana.utility.dto.UtilityResult;

import java.util.List;
import java.util.Optional;

public interface UtilityService {
    List<UtilityResult> findAll();

    Optional<UtilityResult> findById(Long id);

    Utility save(Utility utility);



    void delete(Utility utilityId);

    void show(Long utilityId);

    void hidden(Long utilityId);

    List<UtilityResult> findAllById(Iterable<Long> longs);

    List<UtilityResult> findAllByStatus(StatusUtility status);


    List<UtilityResult> findAllByIdASC(List<Long> utilityIds);
}
