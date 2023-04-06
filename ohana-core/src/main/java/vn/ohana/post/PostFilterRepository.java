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

            if (filter.getKeyword()!=null && filter.getKeyword().trim().length() != 0) {
                Predicate locationPredicate = criteriaBuilder.like(root.get("location"),"%" + filter.getKeyword() + "%");
                Predicate titlePredicate = criteriaBuilder.like(root.get("title"),"%" + filter.getKeyword() + "%");
                Predicate kwPredicate = criteriaBuilder.or(locationPredicate, titlePredicate);
                predicateList.add(kwPredicate);
            }

            if (filter.getMinPrice() != null && filter.getMaxPrice() != null) {
                Predicate minPricePredicate = criteriaBuilder.greaterThan(rentHouse.get("price"),filter.getMinPrice());
                Predicate maxPricePredicate = criteriaBuilder.lessThan(rentHouse.get("price"), filter.getMaxPrice());
                Predicate pricePredicate = criteriaBuilder.or(minPricePredicate, maxPricePredicate);
                predicateList.add(pricePredicate);
            }

            if (filter.getGender() != null && filter.getGender().getId() != null) {
                Predicate renderIdPredicate = criteriaBuilder.equal(rentHouse.get("gender"), filter.getGender().getId());
                predicateList.add(renderIdPredicate);
            }

            if (filter.getUser() != null && filter.getUser().getId() != null) {
                Predicate userIdPredicate = criteriaBuilder.equal(root.get("user"), filter.getUser().getId());
                predicateList.add(userIdPredicate);
            }

            if (filter.getUtilities() != null && !filter.getUtilities().isEmpty()) {


                List<Integer> utilities = filter.getUtilities();
                List<Predicate> expressionList = new ArrayList<>();
                for (Integer utility : utilities) {
                    expressionList.add(criteriaBuilder.equal(criteriaBuilder.function("JSON_CONTAINS",String.class,root.get("utilities"),criteriaBuilder.literal(String.format("%s", utility))),1));
                }

                Predicate joinQuery = criteriaBuilder.and(expressionList.toArray(new Predicate[0]));
                predicateList.add(joinQuery);
            }

            if (filter.getCategoryId() != null) {
                Predicate categoryIdPredicate = criteriaBuilder.equal(root.get("categoryId"), filter.getCategoryId());
                predicateList.add(categoryIdPredicate);
            }

            if (filter.getLocation() != null) {

                if (filter.getLocation().getProvinceId() != null ) {
                    Expression<String> expressionJSON = criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("location"), criteriaBuilder.literal("$.provinceId"));
                    Predicate provinceIdEqual = criteriaBuilder.equal(expressionJSON, filter.getLocation().getProvinceId());
                    predicateList.add(provinceIdEqual);

                }
                if (filter.getLocation().getDistrictId() != null ) {
                    Expression<String> expressionJSON = criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("location"), criteriaBuilder.literal("$.districtId"));
                    Predicate districtIdEqual = criteriaBuilder.equal(expressionJSON, filter.getLocation().getDistrictId());
                    predicateList.add(districtIdEqual);

                }
                if (filter.getLocation().getWardId() != null ) {
                    Expression<String> expressionJSON = criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("location"), criteriaBuilder.literal("$.wardId"));
                    Predicate wardIdEqual = criteriaBuilder.equal(expressionJSON, filter.getLocation().getWardId());
                    predicateList.add(wardIdEqual);

                }
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        }, pageable);
    }

}
