package vn.ohana.category;

import vn.ohana.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Optional<Category> findById(Long id);

    Category getById(Long id);

    Category save(Category category);

    void remove(Long id);
}
