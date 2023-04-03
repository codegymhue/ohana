package vn.ohana.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.User;

@Repository
<<<<<<< HEAD
public interface UserRepository extends JpaRepository<User, Long> {

=======
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Override
    Page<User> findAll(Pageable pageable);

    User findByEmail(String email);

    boolean existsByPhoneOrEmail(String phone, String email);
>>>>>>> hoang_dev
}
