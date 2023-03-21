package vn.sapo.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.Account;
import vn.sapo.entities.Employee;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}