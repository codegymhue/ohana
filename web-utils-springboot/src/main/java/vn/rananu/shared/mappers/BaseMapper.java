package vn.rananu.shared.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

import static vn.rananu.shared.configurations.MapperConfiguration.MODEL_MAPPER_SKIP_NULL_DISABLED;
import static vn.rananu.shared.configurations.MapperConfiguration.MODEL_MAPPER_SKIP_NULL_ENABLED;


public abstract class BaseMapper<DTOResult, Entity, BaseDTO> implements InitializingBean {


    @Autowired
    @Qualifier(MODEL_MAPPER_SKIP_NULL_ENABLED)
    protected ModelMapper modelMapper;
    @Autowired
    @Qualifier(MODEL_MAPPER_SKIP_NULL_DISABLED)
    protected ModelMapper modelMapperSkipNullDisabled;
    private final Class<DTOResult> dtoResultType;
    private final Class<Entity> entityType;

    public BaseMapper() {
        ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
        dtoResultType = (Class) pt.getActualTypeArguments()[0];
        entityType = (Class) pt.getActualTypeArguments()[1];
    }

    @Override
    public void afterPropertiesSet() {
    }

    public DTOResult toDTO(Entity entity) {
        return modelMapper.map(entity, dtoResultType);
    }

    public List<DTOResult> toDTOList(List<Entity> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Entity toEntity(BaseDTO createParam) {
        return modelMapper.map(createParam, entityType);
    }


    public void transferFields(BaseDTO updateParam, Entity entity) {
        transferFields(updateParam, entity, false);
    }

    public void transferFields(BaseDTO updateParam, Entity entity, boolean skipNullEnabled) {
        if (skipNullEnabled)
            modelMapper.map(updateParam, entity);
        else
            modelMapperSkipNullDisabled.map(updateParam, entity);
    }


}
