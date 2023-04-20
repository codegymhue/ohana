package vn.ohana.category;

import org.springframework.transaction.annotation.Transactional;
import vn.ohana.category.dto.CategoryResult;
import vn.ohana.category.dto.CategoryCreationParam;
import vn.ohana.category.dto.CategoryUpdateParam;
import vn.ohana.entities.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResult> findAll();

    CategoryResult getById(Long id);

    CategoryResult create(CategoryCreationParam param);

    CategoryResult update(CategoryUpdateParam param);

    void deleteById(Long id);

    Category findById(Long id);

    CategoryResult updateStatusCategory(Long categoryId, String status);

    Long count();
}
