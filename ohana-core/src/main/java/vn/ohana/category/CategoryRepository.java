package vn.ohana.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ohana.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
