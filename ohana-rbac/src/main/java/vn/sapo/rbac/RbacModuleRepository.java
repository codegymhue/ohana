package vn.sapo.rbac;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.rbac.RbacModule;

@Repository
public interface RbacModuleRepository extends JpaRepository<RbacModule, Integer> {
}