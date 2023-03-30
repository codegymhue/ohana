package vn.ohana.utility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.StatusUtility;
import vn.ohana.entities.Utility;

import java.util.List;
import java.util.Set;

@Repository
public interface UtilityRepository extends JpaRepository<Utility, Long> {
    List<Utility> findAllByIdIn(Set<Long> ids);

    List<Utility> findAllByStatus(StatusUtility status);


}
