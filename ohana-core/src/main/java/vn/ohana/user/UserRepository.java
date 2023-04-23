package vn.ohana.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.Role;
import vn.ohana.entities.User;
import vn.ohana.entities.UserStatus;
import vn.ohana.report.dto.DateReportResult;
import vn.ohana.user.dto.LoginParam;
import vn.ohana.user.dto.LoginResult;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByRoleNot(Role role, Pageable pageable);

    @Override
    Page<User> findAll(Pageable pageable);

    Optional<User> findByEmail(String email);

    Long countUsersByStatus(UserStatus status);

    boolean existsByPhoneOrEmail(String phone, String email);

    boolean existsByEmail(String Email);

    boolean existsByPhoneAndIdNot(String phone, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    User findByEmailAndPassword(String email, String password);

    User findByCode(String code);

    @Procedure(procedureName = "coutUserByMonhtBetweenDate")
    List<Object> countByMonthBetweenDate(Instant startDate, Instant endDate);
}
