package vn.ohana.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.ohana.entities.*;
import vn.ohana.post.dto.PostFilterParam;

import javax.persistence.criteria.*;
import java.time.Instant;
import java.util.*;


@Repository
public interface PostFilterRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    default Page<Post> findAllByFilters(PostFilterParam filter, Pageable pageable) {
        return findAll((root, criteriaQuery, criteriaBuilder) -> {

            Join<Post, RentHouse> rentHouse = root.join("rentHouse");

            List<Predicate> predicateList = new ArrayList<>();

            if (filter.getKeyword() != null && filter.getKeyword().trim().length() != 0) {
                Predicate locationPredicate = criteriaBuilder.like(root.get("location"), "%" + filter.getKeyword() + "%");
                Predicate titlePredicate = criteriaBuilder.like(root.get("title"), "%" + filter.getKeyword() + "%");
                Predicate kwPredicate = criteriaBuilder.or(locationPredicate, titlePredicate);
                predicateList.add(kwPredicate);
            }

            if (filter.getPriceStarts() != null && filter.getPriceEnds() != null) {
                Predicate priceStartsPredicate = criteriaBuilder.greaterThan(rentHouse.get("roomPrice"), filter.getPriceStarts());
                Predicate priceEndsPredicate = criteriaBuilder.lessThan(rentHouse.get("roomPrice"), filter.getPriceEnds());
                Predicate pricePredicate = criteriaBuilder.and(priceStartsPredicate, priceEndsPredicate);
                predicateList.add(pricePredicate);
            }

            if (filter.getGender() != null && filter.getGender().getId() != null) {
                Predicate genderIdPredicate = criteriaBuilder.equal(rentHouse.get("gender"), filter.getGender().getId());
                predicateList.add(genderIdPredicate);
            }

            if (filter.getStatus() != null) {
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), filter.getStatus());
                predicateList.add(statusPredicate);
            }

            if (filter.getUser() != null && filter.getUser().getId() != null) {
                Predicate userIdPredicate = criteriaBuilder.equal(root.get("user"), filter.getUser().getId());
                predicateList.add(userIdPredicate);
            }

            if (filter.getUtilities() != null && !filter.getUtilities().isEmpty()) {


                List<Integer> utilities = filter.getUtilities();
                List<Predicate> expressionList = new ArrayList<>();
                for (Integer utility : utilities) {
                    expressionList.add(criteriaBuilder.equal(criteriaBuilder.function("JSON_CONTAINS", String.class, root.get("utilities"), criteriaBuilder.literal(String.format("%s", utility))), 1));
                }

                Predicate joinQuery = criteriaBuilder.and(expressionList.toArray(new Predicate[0]));
                predicateList.add(joinQuery);
            }

            if (filter.getCategories() != null) {
                Predicate categoryPredicate = criteriaBuilder.equal(root.get("category"), filter.getCategories());
                predicateList.add(categoryPredicate);
            }

            if (filter.getLocationFilter() != null) {

                if (filter.getLocationFilter().getLine1() != null) {
                    Expression<String> expressionJSON = criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("location"), criteriaBuilder.literal("$.line1"));
                    Predicate line1Equal = criteriaBuilder.equal(expressionJSON, filter.getLocationFilter().getLine1());
                    predicateList.add(line1Equal);

                }
                if (filter.getLocationFilter().getProvinceId() != null) {
                    Expression<String> expressionJSON = criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("location"), criteriaBuilder.literal("$.provinceId"));
                    Predicate provinceIdEqual = criteriaBuilder.equal(expressionJSON, filter.getLocationFilter().getProvinceId());
                    predicateList.add(provinceIdEqual);

                }
                if (filter.getLocationFilter().getDistrictId() != null) {
                    Expression<String> expressionJSON = criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("location"), criteriaBuilder.literal("$.districtId"));
                    Predicate districtIdEqual = criteriaBuilder.equal(expressionJSON, filter.getLocationFilter().getDistrictId());
                    predicateList.add(districtIdEqual);

                }
                if (filter.getLocationFilter().getWardId() != null) {
                    Expression<String> expressionJSON = criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("location"), criteriaBuilder.literal("$.wardId"));
                    Predicate wardIdEqual = criteriaBuilder.equal(expressionJSON, filter.getLocationFilter().getWardId());
                    predicateList.add(wardIdEqual);

                }
            }

            if (filter.getCreatedAtStart() != null) {
                Predicate datePeriodPredicate = criteriaBuilder.between(root.get("createdAt"), filter.getCreatedAtStart(), Instant.now());
                if (filter.getCreatedAtEnd() != null) {
                    datePeriodPredicate = criteriaBuilder.between(root.get("createdAt"), filter.getCreatedAtStart(), filter.getCreatedAtEnd());
                }
                predicateList.add(datePeriodPredicate);
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        }, pageable);
    }
}
