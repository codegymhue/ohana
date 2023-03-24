package vn.ohana.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.Category;
import vn.ohana.category.dto.CategoryResult;
import vn.sapo.shared.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResult> findAll() {
        return categoryRepository.findAll();
    }

    @Override

    public CategoryResult findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("category.exception.notFound"));
    }


}
