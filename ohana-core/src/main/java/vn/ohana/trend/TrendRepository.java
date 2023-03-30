package vn.ohana.trend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.Trend;

import java.util.List;

@Repository
public interface TrendRepository extends JpaRepository<Trend, Long> {

}
