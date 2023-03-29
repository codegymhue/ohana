package vn.ohana.post;


import org.springframework.stereotype.Component;
import vn.ohana.entities.*;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;
import vn.rananu.shared.mappers.BaseMapper;


@Component
public class PostMapper extends BaseMapper<PostResult, Post, PostUpdateParam> {

}
