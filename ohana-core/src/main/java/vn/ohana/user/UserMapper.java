package vn.ohana.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.ohana.PostMedia;
import vn.ohana.User;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.post.PostMediaService;
import vn.ohana.user.dto.BaseUser;
import vn.ohana.user.dto.SignUpParam;
import vn.ohana.user.dto.UpdateUserParam;
import vn.ohana.user.dto.UserResult;
import vn.rananu.mappers.BaseMapper;

import java.util.Optional;


@Component
public class UserMapper extends BaseMapper<UserResult, User, BaseUser> {

    @Autowired
    PostMediaService postMediaService;

    public UserResult toDTO(User user) {
        UserResult userResult = new UserResult();
        userResult.setId(user.getId());
        userResult.setFullName(user.getFullName());
//        userResult.setEmail(user.getEmail());

        if (user.getEmail().contains("@")) {
            userResult.setEmail(user.getEmail());
        } else {
            userResult.setEmail(null);
        }
        userResult.setPhone(user.getPhone());
//        userResult.setPassword(user.getPassword());
//        userResult.setRole(user.getRole());
//        userResult.setStatus(user.getStatus());

        if (user.getThumbnailId() != null) {
            Optional<PostMedia> option = postMediaService.findById(user.getThumbnailId());
            option.ifPresent(postMedia -> userResult.setThumbnailUrl(option.get().getFileUrl()));
//            userResult.setThumbnailId(user.getThumbnailId());
        }
//        userResult.setThumbnailUrl(postMedia.get().getFileUrl());
        userResult.setDescription(user.getDescription());
        userResult.setAddress(user.getAddress());
        userResult.setRegisteredAt(user.getRegisteredAt());
        userResult.setLastLogin(user.getLastLogin());
        return userResult;
    }

    public User toEntity(GooglePojo googlePojo) {
        User user = new User();
        user.setPassword(googlePojo.getId());
        user.setEmail(googlePojo.getEmail());
        user.setFullName(googlePojo.getName());
        user.setThumbnailId(googlePojo.getPicture());
        return user;
    }
}
