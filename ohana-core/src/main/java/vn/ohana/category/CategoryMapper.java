package vn.ohana.category;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import vn.ohana.Category;
import vn.ohana.category.dto.BaseCategory;
import vn.ohana.category.dto.CategoryResult;
import vn.ohana.category.dto.CreateCategoryParam;
import vn.ohana.category.dto.UpdateCategoryParam;
import vn.rananu.mappers.BaseMapper;

import java.util.List;
import java.util.stream.Collectors;

import static vn.rananu.configurations.MapperConfiguration.MODEL_MAPPER_SKIP_NULL_DISABLED;
import static vn.rananu.configurations.MapperConfiguration.MODEL_MAPPER_SKIP_NULL_ENABLED;

@Component
public class CategoryMapper extends BaseMapper<CategoryResult, Category, BaseCategory> {
//    @Autowired
//    @Qualifier(MODEL_MAPPER_SKIP_NULL_ENABLED)
//    protected ModelMapper modelMapper;
//    @Autowired
//    @Qualifier(MODEL_MAPPER_SKIP_NULL_DISABLED)
//    protected ModelMapper modelMapperSkipNullDisabled;
//
//    public CategoryResult toDTO(Category entity) {
//        return modelMapper.map(entity, CategoryResult.class);
//    }
//
//    public List<CategoryResult> toDTOList(List<Category> entities) {
//        return entities.stream().map(this::toDTO).collect(Collectors.toList());
//    }
//
//    public Category toEntity(CreateCategoryParam createSupplierParam) {
//        return modelMapper.map(createSupplierParam, Category.class);
//    }
//
//    public void transferFields(UpdateCategoryParam updateSupplierParam, Category category) {
//        modelMapperSkipNullDisabled.map(updateSupplierParam, category);
//    }

}
