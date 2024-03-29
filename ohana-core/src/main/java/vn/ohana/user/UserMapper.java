package vn.ohana.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.ohana.entities.User;
import vn.ohana.google.dto.GooglePojo;
import vn.ohana.post.PostMediaService;
import vn.ohana.user.dto.BaseUser;
import vn.ohana.user.dto.LoginResult;
import vn.ohana.user.dto.UserPrincipal;
import vn.ohana.user.dto.UserResult;
import vn.ohana.user.dto.UserUpdateParam;
import vn.rananu.shared.mappers.BaseMapper;


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

//
//    public LoginResult toLoginResultDTO(User user) {
//        return modelMapper.map(user, LoginResult.class);
//    }

//    public LoginResult toLoginResultDTO(User user) {
//        LoginResult loginResult = new LoginResult();
//        loginResult.setId(user.getId());
//        loginResult.setFullName(user.getFullName());
//        loginResult.setEmail(user.getEmail());
//        loginResult.setPhone(user.getPhone());
//        loginResult.setDescription(user.getDescription());
//        loginResult.setAddress(user.getAddress());
//        loginResult.setPassword(user.getPassword());
//        if (user.getThumbnailId() != null) {
//            Optional<PostMedia> option = postMediaService.findById(user.getThumbnailId());
//            option.ifPresent(postMedia -> loginResult.setThumbnailUrl(option.get().getFileUrl()));
//        }
//        return loginResult;
//    }

    public LoginResult toLoginResultDTO(User user){
        return modelMapper.map(user,LoginResult.class);
    }

    public UserResult toUserResultDTO(User user){
        return modelMapper.map(user,UserResult.class);
    }

    public UserUpdateParam toUserUpdateParamDTO(User user){
        return modelMapper.map(user,UserUpdateParam.class);
    }


    public LoginResult toLoginResult(User user) {
        return modelMapper.map(user, LoginResult.class);
    }

    public User toGooglePojo(GooglePojo googlePojo) {
        return modelMapper.map(googlePojo,User.class);
    }

    public UserResult toUserDTO(User user) {
        return modelMapper.map(user,UserResult.class);
    }


    public UserPrincipal toUserPrinciple(User user) {
        return modelMapper.map(user, UserPrincipal.class);
    }
}
