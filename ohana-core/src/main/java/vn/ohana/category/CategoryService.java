package vn.ohana.category;

import vn.ohana.category.dto.CategoryResult;
import vn.ohana.category.dto.CreateCategoryParam;
import vn.ohana.category.dto.UpdateCategoryParam;

import java.util.List;

public interface CategoryService {
    List<CategoryResult> findAll();

    CategoryResult getById(Long id);

    CategoryResult create(CreateCategoryParam param);

    CategoryResult update(UpdateCategoryParam param);

    void deleteById(Long id);
}
