package vn.ohana.renthouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.RentHouse;

@Repository
public interface RentHouseRepository extends JpaRepository<RentHouse,String> {
}
