package vn.ohana.post;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.ohana.entities.*;
import vn.ohana.location.dto.DataSearchResult;
import vn.ohana.post.dto.PostCreateParam;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;
import vn.ohana.user.dto.UserResult;
import vn.ohana.utility.dto.UtilityResult;
import vn.rananu.shared.mappers.BaseMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class PostMapper extends BaseMapper<PostResult, Post, PostUpdateParam> {

    public Page<PostResult> toDtoPage(Page<Post> page) {
        return page.map(this::toDTO);
    }

    public Post toPost(PostCreateParam postCreateParam){
        return modelMapper.map(postCreateParam,Post.class);
    }
}
