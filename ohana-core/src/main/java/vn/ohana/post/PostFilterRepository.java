package vn.ohana.post;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.ohana.entities.*;

import vn.ohana.location.dto.LocationParam;
import vn.ohana.post.dto.PostFilterParam;


import javax.persistence.criteria.*;
import java.util.*;


@Repository
public interface PostFilterRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    default Page<Post> findAllByFilters(PostFilterParam filter, Pageable pageable) {
        return findAll((root, criteriaQuery, criteriaBuilder) -> {
            Join<Post, RentHouse> rentHouse = root.join("rentHouse");
            List<Predicate> predicateList = new ArrayList<>();
            if (filter.getMinPrice() != null && filter.getMaxPrice() != null) {

                Predicate minPricePredicate = criteriaBuilder.greaterThan(rentHouse.get("price"),filter.getMinPrice());
                Predicate maxPricePredicate = criteriaBuilder.lessThan(rentHouse.get("price"), filter.getMaxPrice());
                Predicate pricePredicate = criteriaBuilder.or(minPricePredicate, maxPricePredicate);
                predicateList.add(pricePredicate);
            }
            if (filter.getGender() != null) {
                Predicate renderPredicate = criteriaBuilder.equal(rentHouse.get("gender"), filter.getGender());
                predicateList.add(renderPredicate);
            }
            if (filter.getCategoryId() != null) {
                Predicate categoryIdPredicate = criteriaBuilder.equal(root.get("categoryId"), filter.getCategoryId());
                predicateList.add(categoryIdPredicate);
            }

            if (filter.getLocation() != null) {
                Predicate locationParamPredicate = criteriaBuilder.equal(root.get("location"), filter.getLocation());
                predicateList.add(locationParamPredicate);
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        }, pageable);
    }

}
