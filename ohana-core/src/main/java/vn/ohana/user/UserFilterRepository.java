package vn.ohana.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.ohana.entities.User;
import vn.ohana.user.dto.UserFilterParam;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserFilterRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    default Page<User> findAllByFilters(UserFilterParam filter, Pageable pageable){
        return  findAll((root,criteriaQuery,criteriaBuilder)->{
            List<Predicate> predicateList = new ArrayList<>();

            if (filter.getKeyword()!=null) {
                Predicate fullNamePredicate = criteriaBuilder.like(root.get("fullName"),"%" + filter.getKeyword() + "%");
                Predicate emailPredicate = criteriaBuilder.like(root.get("email"),"%" + filter.getKeyword() + "%");
                Predicate addressPredicate = criteriaBuilder.like(root.get("address"),"%" + filter.getKeyword() + "%");
                Predicate phonePredicate = criteriaBuilder.like(root.get("phone"), "%" + filter.getKeyword() + "%");
                Predicate descriptionPredicate = criteriaBuilder.like(root.get("description"), "%" + filter.getKeyword() + "%");
                Predicate kwPredicate = criteriaBuilder.or(fullNamePredicate, emailPredicate, addressPredicate, phonePredicate, descriptionPredicate);
                predicateList.add(kwPredicate);
            }

            if (filter.getRole()!=null) {
                Predicate rolePredicare = criteriaBuilder.equal(root.get("role"), filter.getRole());
                predicateList.add(rolePredicare);
            }

            if (filter.getStatusList()!=null && !filter.getStatusList().isEmpty()) {
                Predicate statusListPredicate = criteriaBuilder.or(root.get("status").in(filter.getStatusList()));
                predicateList.add(statusListPredicate);
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        },pageable);
    }
}
