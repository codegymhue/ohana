package vn.ohana.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.Role;
import vn.ohana.entities.User;
import vn.ohana.entities.UserStatus;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
