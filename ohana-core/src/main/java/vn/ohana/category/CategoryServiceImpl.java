package vn.ohana.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.Category;
import vn.ohana.category.dto.CategoryResult;
import vn.ohana.category.dto.CreateCategoryParam;
import vn.ohana.category.dto.UpdateCategoryParam;
import vn.rananu.shared.exceptions.NotFoundException;
import vn.rananu.shared.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
//
//        List<CategoryResult> dtoList = new ArrayList<>();
//
//        for (Category category : entities) {
//            CategoryResult dto = categoryMapper.toDTO(category);
//////            dtoList.add(dto);
//        }
//       return dtoList;
//      return   entities
//              .stream()
//              .map( categoryMapper::toDTO)
//              .collect(Collectors.toList());


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
        Category category = findById(param.getId());
        categoryMapper.transferFields(param, category);
        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

}
