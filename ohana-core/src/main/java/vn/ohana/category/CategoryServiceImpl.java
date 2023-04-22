package vn.ohana.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.category.dto.CategoryCreationParam;
import vn.ohana.category.dto.CategoryResult;
import vn.ohana.category.dto.CategoryUpdateParam;
import vn.ohana.entities.Category;
import vn.ohana.entities.Post;
import vn.ohana.entities.StatusCategory;
import vn.ohana.post.dto.PostResult;
import vn.rananu.shared.exceptions.NotFoundException;
import vn.rananu.shared.exceptions.ValidationException;

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

    @Override
    public Page<CategoryResult> findAllPaging(Pageable pageable) {
        return toDtoPage(categoryRepository.findAll(pageable));
    }

    private Page<CategoryResult> toDtoPage(Page<Category> page) {
        return page.map(entity -> categoryMapper.toDTO(entity));
    }

    @Override
    public Page<CategoryResult> findAllByStatusPaging(StatusCategory status, Pageable pageable) {
        if (status == null) {
            return toDtoPage(categoryRepository.findAll(pageable));
        }
        return toDtoPage(categoryRepository.findAllByStatus(status, pageable));
    }

    @Override
    public Long count() {
        return categoryRepository.count();
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category.exception.notFound"));
    }

    @Override
    public CategoryResult getById(Long id) {
        return categoryMapper.toDTO(findById(id));
    }

    private void validationByTitle(String title) {
        if (categoryRepository.existsByTitle(title)) {
            throw new ValidationException("title", "category.validation.title.existed");
        }
    }

    @Override
    @Transactional
    public CategoryResult create(CategoryCreationParam param) {
        validationByTitle(param.getTitle());
        Category category = categoryMapper.toEntity(param)
                .setStatus(StatusCategory.SHOW);
        category = categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional
    public CategoryResult update(CategoryUpdateParam param) {
        Category category = findById(param.getId());
        categoryMapper.transferFields(param, category,true);

        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }


    @Override
    @Transactional
    public CategoryResult updateStatusCategory(Long categoryId, String status) {
        StatusCategory statusCategory = StatusCategory.parseStatusCategory(status);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("category.exception.notFound"));
        category.setStatus(statusCategory);
        return categoryMapper.toDTO(category);
    }




}
