package vn.ohana.user;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.ohana.entities.User;
import vn.ohana.user.dto.BaseUser;
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
}
