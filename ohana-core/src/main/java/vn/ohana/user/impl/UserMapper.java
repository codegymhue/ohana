package vn.ohana.user.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.ohana.entities.User;
import vn.ohana.user.dto.BaseUser;
import vn.ohana.user.dto.LoginResult;
import vn.ohana.user.dto.UserResult;
import vn.rananu.shared.mappers.BaseMapper;


@Component
public class UserMapper extends BaseMapper<UserResult, User, BaseUser> {

//    public void transferFieldsSkipNull(BaseUser updateParam, User category) {
//        modelMapper.map(updateParam,category);
//    }

    public Page<UserResult> toDtoPage(Page<User> page) {
        return page.map(this::toDTO);
    }

    public LoginResult toLoginResultDTO(User user) {
        return modelMapper.map(user, LoginResult.class);
    }

    public User toGooglePojo(GooglePojo googlePojo) {
        return modelMapper.map(googlePojo,User.class);
    }

    public UserResult toUserDTO(User user) {
        return modelMapper.map(user,UserResult.class);
    }


}
