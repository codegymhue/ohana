package vn.ohana.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.ohana.entities.PostMedia;
import vn.ohana.entities.User;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.post.PostMediaService;
import vn.ohana.user.dto.BaseUser;
import vn.ohana.user.dto.LoginResult;
import vn.ohana.user.dto.UserResult;
import vn.rananu.shared.mappers.BaseMapper;

import java.util.Optional;


@Component
public class UserMapper extends BaseMapper<UserResult, User, BaseUser> {

//    public void transferFieldsSkipNull(BaseUser updateParam, User category) {
//        modelMapper.map(updateParam,category);
//    }
@Autowired
PostMediaService postMediaService;
    public Page<UserResult> toDtoPage(Page<User> page) {
        return page.map(this::toDTO);
    }


//    public LoginResult toLoginResultDTO(User user) {
//        return modelMapper.map(user, LoginResult.class);
//    }

    public LoginResult toLoginResultDTO(User user) {
        LoginResult loginResult = new LoginResult();
        loginResult.setId(user.getId());
        loginResult.setFullName(user.getFullName());
        loginResult.setEmail(user.getEmail());
        loginResult.setPhone(user.getPhone());
        loginResult.setPassword(user.getPassword());
        if (user.getThumbnailId() != null) {
            Optional<PostMedia> option = postMediaService.findById(user.getThumbnailId());
            option.ifPresent(postMedia -> loginResult.setThumbnailUrl(option.get().getFileUrl()));
        }
        return loginResult;
    }


    public User toGooglePojo(GooglePojo googlePojo) {
        return modelMapper.map(googlePojo,User.class);
    }

    public UserResult toUserDTO(User user) {
        return modelMapper.map(user,UserResult.class);
    }


}
