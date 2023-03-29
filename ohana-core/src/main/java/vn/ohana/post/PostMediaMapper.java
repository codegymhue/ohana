package vn.ohana.post;


import org.springframework.stereotype.Component;
import vn.ohana.entities.PostMedia;
import vn.ohana.post.dto.PostMediaParam;
import vn.ohana.post.dto.PostMediaResult;
import vn.rananu.shared.mappers.BaseMapper;


@Component
public class PostMediaMapper extends BaseMapper<PostMediaResult,PostMedia,PostMediaParam> {
}
