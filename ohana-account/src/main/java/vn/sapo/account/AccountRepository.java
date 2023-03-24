package vn.sapo.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}