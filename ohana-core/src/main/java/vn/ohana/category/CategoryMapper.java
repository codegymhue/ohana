package vn.ohana.category;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import vn.ohana.Category;
import vn.ohana.category.dto.CategoryResult;
import vn.ohana.category.dto.CreateCategoryParam;
import vn.ohana.category.dto.UpdateCategoryParam;

import static vn.sapo.shared.configurations.MapperConfiguration.MODEL_MAPPER_SKIP_NULL_DISABLED;
import static vn.sapo.shared.configurations.MapperConfiguration.MODEL_MAPPER_SKIP_NULL_ENABLED;

@Component
public class CategoryMapper {
    @Autowired
    @Qualifier(MODEL_MAPPER_SKIP_NULL_ENABLED)
    private ModelMapper modelMapper;
    @Autowired
    @Qualifier(MODEL_MAPPER_SKIP_NULL_DISABLED)
    private ModelMapper modelMapperSkipNullDisabled;


    public CategoryResult toDTO(Category category) {
        return modelMapper.map(category, CategoryResult.class);
    }


    public Category toModel(CreateCategoryParam createSupplierParam) {
        return modelMapper.map(createSupplierParam, Category.class);
    }

    public void transferFields(UpdateCategoryParam updateSupplierParam, Category category) {
        modelMapperSkipNullDisabled.map(updateSupplierParam, category);
    }

}
