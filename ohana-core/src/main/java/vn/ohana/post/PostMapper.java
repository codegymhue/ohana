package vn.ohana.post;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.ohana.entities.*;
import vn.ohana.location.dto.DataSearchResult;
import vn.ohana.post.dto.PostResult;
import vn.ohana.post.dto.PostUpdateParam;
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

    public DataSearchResult toDataSearchResult(Post post, String locationUnsigned) {
        DataSearchResult dataSearchResult = new DataSearchResult();
//        dataSearchResult.setPostId(post.getId());
        dataSearchResult.setProvinceName(post.getLocation().getProvinceName());
//        if (post.getLocation().getProvinceUnsignedName().contains(locationUnsigned)) {
//            dataSearchResult.setLocationDetail(post.getLocation().getProvinceName());
//        }
        if (post.getLocation().getDistrictUnsignedName().contains(locationUnsigned)) {
            dataSearchResult.setDistrictName(post.getLocation().getDistrictName());
            dataSearchResult.setLocationDetail(post.getLocation().getDistrictName() + " ," + post.getLocation().getProvinceName());
        } else if (post.getLocation().getWardUnsignedName().contains(locationUnsigned)) {
            dataSearchResult.setWardName(post.getLocation().getWardName());
            dataSearchResult.setLocationDetail(post.getLocation().getWardName() + " ," + post.getLocation().getProvinceName());
        } else if (post.getLocation().getProvinceUnsignedName().contains(locationUnsigned) && !post.getLocation().getWardName().contains(locationUnsigned)) {
            dataSearchResult.setLocationDetail(null);
        } else {
            dataSearchResult.setLocationDetail(post.getLocation().getLine1() + " ," + post.getLocation().getProvinceName());
        }
        return dataSearchResult;
    }
}
