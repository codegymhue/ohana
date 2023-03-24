package vn.ohana.category;

import vn.ohana.Category;
import vn.ohana.category.dto.CategoryResult;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryResult> findAll();

    CategoryResult findById(Long id);
}
