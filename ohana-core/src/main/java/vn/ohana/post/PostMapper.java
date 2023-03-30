package vn.ohana.post;


import org.springframework.stereotype.Component;
import vn.ohana.entities.*;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;
import vn.rananu.shared.mappers.BaseMapper;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class PostMapper extends BaseMapper<PostResult, Post, PostUpdateParam> {
    @Override
    public List<PostResult> toDTOList(List<Post> entities) {
        return entities.stream().map(entity->modelMapperSkipNullDisabled.map(entity,PostResult.class)).collect(Collectors.toList());
    }
}
