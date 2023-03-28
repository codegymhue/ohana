package vn.ohana.category;

import org.springframework.stereotype.Component;
import vn.ohana.category.dto.BaseCategory;
import vn.ohana.category.dto.CategoryResult;
import vn.ohana.entities.Category;
import vn.rananu.shared.mappers.BaseMapper;

@Component
public class CategoryMapper extends BaseMapper<CategoryResult, Category, BaseCategory> {
    @Override
    public void afterPropertiesSet() {

    }
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
//    public Category toEntity(BaseCategory createSupplierParam) {
//        return modelMapper.map(createSupplierParam, Category.class);
//    }
//
//    public void transferFields(UpdateCategoryParam updateSupplierParam, Category category) {
//        modelMapperSkipNullDisabled.map(updateSupplierParam, category);
//    }


}
