package vn.ohana.gender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender,Long> {

}
