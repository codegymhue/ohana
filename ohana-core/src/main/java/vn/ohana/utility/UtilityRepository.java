package vn.ohana.utility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.StatusUtility;
import vn.ohana.Utility;

import java.util.List;

@Repository
public interface UtilityRepository extends JpaRepository<Utility, Long> {
    List<Utility> findAllById(Iterable<Long> ids);

    List<Utility> findAllByStatus(StatusUtility status);



}