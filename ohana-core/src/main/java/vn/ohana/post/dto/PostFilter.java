package vn.ohana.post.dto;

import vn.ohana.entities.Gender;
import vn.ohana.entities.RentHouse;
import vn.ohana.entities.StatusPost;

import java.math.BigDecimal;
import java.util.List;

public class PostFilter {
    private String filter;
    private List<StatusPost> statuses;
    private Gender gender;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
