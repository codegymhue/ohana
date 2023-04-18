package vn.ohana.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.Role;
import vn.ohana.entities.User;
import vn.ohana.user.dto.UserFilterParam;

import javax.persistence.criteria.Predicate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserFilterRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    default Page<User> findAllByFilters(UserFilterParam filter, Pageable pageable){
        return  findAll((root,criteriaQuery,criteriaBuilder)->{
            List<Predicate> predicateList = new ArrayList<>();

            Predicate notAdminPredicate = criteriaBuilder.not(criteriaBuilder.equal(root.get("role"), Role.ADMIN));
            predicateList.add(notAdminPredicate);

            if (filter.getKeyword()!=null && filter.getKeyword().trim().length() != 0) {
                Predicate fullNamePredicate = criteriaBuilder.like(root.get("fullName"),"%" + filter.getKeyword() + "%");
                Predicate emailPredicate = criteriaBuilder.like(root.get("email"),"%" + filter.getKeyword() + "%");
                Predicate addressPredicate = criteriaBuilder.like(root.get("address"),"%" + filter.getKeyword() + "%");
                Predicate phonePredicate = criteriaBuilder.like(root.get("phone"), "%" + filter.getKeyword() + "%");
                Predicate descriptionPredicate = criteriaBuilder.like(root.get("description"), "%" + filter.getKeyword() + "%");
                Predicate kwPredicate = criteriaBuilder.or(fullNamePredicate, emailPredicate, addressPredicate, phonePredicate, descriptionPredicate);
                predicateList.add(kwPredicate);
            }

            if (filter.getRole()!=null) {
                Predicate rolePredicate = criteriaBuilder.equal(root.get("role"), filter.getRole());
                predicateList.add(rolePredicate);
            }

            if (filter.getStatus()!=null) {
                Predicate statusListPredicate = criteriaBuilder.equal(root.get("status"),filter.getStatus());
                predicateList.add(statusListPredicate);
            }

            if (filter.getCreatedAtStart() != null) {
                Predicate datePeriodPredicate = criteriaBuilder.between(root.get("createdAt"), filter.getCreatedAtStart(), Instant.now());
                if (filter.getCreatedAtEnd() != null) {
                    datePeriodPredicate = criteriaBuilder.between(root.get("createdAt"), filter.getCreatedAtStart(), filter.getCreatedAtEnd());
                }
                predicateList.add(datePeriodPredicate);
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        },pageable);
    }
}
