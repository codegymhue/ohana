package vn.ohana.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.Role;
import vn.ohana.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByRoleNot(Role role, Pageable pageable);
    @Override
    Page<User> findAll(Pageable pageable);

    User findByEmail(String email);


    boolean existsByPhoneOrEmail(String phone, String email);

    boolean existsByEmail(String Email);
    boolean existsByPhoneAndIdNot(String phone, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);
    User findByEmailAndPassword(String email, String password);

    User findByCode(String code);

}
