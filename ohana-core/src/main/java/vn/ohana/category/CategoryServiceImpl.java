package vn.ohana.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.Category;
import vn.ohana.category.dto.CategoryResult;
import vn.ohana.category.dto.CreateCategoryParam;
import vn.ohana.category.dto.UpdateCategoryParam;
import vn.rananu.shared.exceptions.NotFoundException;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResult> findAll() {
        List<Category> entities = categoryRepository.findAll();
        return categoryMapper.toDTOList(entities);
    }

    @Transactional(readOnly = true)
    Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category.exception.notFound"));
    }

    @Override
    public CategoryResult getById(Long id) {
        return categoryMapper.toDTO(findById(id));
    }

    @Override
    @Transactional
    public CategoryResult create(CreateCategoryParam param) {
        Category category = categoryMapper.toEntity(param);
        category = categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional
    public CategoryResult update(UpdateCategoryParam param) {
        Category category;
        category = findById(param.getId());
        categoryMapper.transferFields(param, category);
        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

}
