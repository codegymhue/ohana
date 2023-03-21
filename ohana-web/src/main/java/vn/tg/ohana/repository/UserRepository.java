package vn.tg.ohana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.tg.ohana.repository.model.*;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getByEmailOrPhone(String email, String phone);

    boolean existsByPhoneOrEmail(String phone, String email);

    User findByEmail(String email);

    List<User> findAllByStatus(UserStatus status);

    @Query("SELECT count(u.status) FROM User u")
    Long getQuantityUser(UserStatus userStatus);
    boolean existsByPhoneOrEmailAndPasswordAndRole(String phone, String email, String password, Role role);

}
