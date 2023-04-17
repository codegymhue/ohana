package vn.ohana.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.Role;
import vn.ohana.entities.User;
import vn.ohana.entities.UserStatus;
import vn.ohana.user.dto.LoginParam;
import vn.ohana.user.dto.LoginResult;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByRoleNot(Role role, Pageable pageable);
    @Override
    Page<User> findAll(Pageable pageable);

    User findByEmail(String email);


    boolean existsByPhoneOrEmail(String phone, String email);

    boolean existsByEmail(String Email);

    User findByEmailAndPassword(String email, String password);
}
