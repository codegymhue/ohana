package vn.ohana.utility;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.StatusUtility;
import vn.ohana.entities.Utility;

import java.util.List;
import java.util.Set;

@Repository
public interface UtilityRepository extends JpaRepository<Utility, Integer> {
    Page<Utility> findByStatus(StatusUtility status, Pageable pageable);
    List<Utility> findAllByIdIn(Set<Integer> ids);

    List<Utility> findAllByStatus(StatusUtility status);



}
