package vn.sapo.rbac;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.rbac.RbacRole;

@Repository
public interface RbacRoleRepository extends JpaRepository<RbacRole, Integer> {
}