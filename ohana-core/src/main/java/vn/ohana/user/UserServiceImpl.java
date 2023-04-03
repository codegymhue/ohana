package vn.ohana.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.User;
import vn.ohana.entities.UserStatus;
import vn.ohana.post.PostMediaService;
import vn.ohana.user.dto.UserFilterParam;
import vn.ohana.user.dto.UserResult;
import vn.ohana.user.dto.UserUpdateParam;
import vn.rananu.shared.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserFilterRepository userFilterRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PostMediaService postMediaService;

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category.exception.notFound"));
    }


    public Page<UserResult> getAll(Pageable pageable) {
        Page<User> page = findAll(pageable);
        return userMapper.toDtoPage(page);
    }

    @Override
    @Transactional
    public UserResult update(UserUpdateParam updateParam) {
        User user = findById(updateParam.getId());
        userMapper.transferFields(updateParam, user);
        return userMapper.toDTO(user);
    }

    @Override
    public Page<UserResult> filter(UserFilterParam filter, Pageable pageable) {
        Page<User> page = userFilterRepository.findAllByFilters(filter, pageable);
        return userMapper.toDtoPage(page);
    }

    //    @Override
//    @Transactional
//    public void deactivateAllByIds(Set[] ids) {
//        for (Long id : ids) {
//            userRepository.findById(id)
//                    .map(user -> user.setStatus(UserStatus.NOT_ACTIVATED))
//                    .orElseThrow(() -> new NotFoundException("user.notFound"));
//        }
//    }
    @Override
    @Transactional
    public Map<Long, String> modifyStatusByIds(Set<Long> ids, String statusRaw) {
        UserStatus status = UserStatus.parseUserStatus(statusRaw);
        Map<Long, String> result = new HashMap<>();
        Iterable<User> entities = userRepository.findAllById(ids);
        entities.forEach(entity -> {
            entity.setStatus(status);
            result.put(entity.getId(), "successful");
        });

        List<Long> entityIds = StreamSupport.stream(entities.spliterator(), false).map(User::getId).collect(Collectors.toList());
        ids.forEach(id -> {
            if (!entityIds.contains(id))
                result.put(id, "failed");
        });
        return result;
    }

    @Override
    @Transactional
    public void modifyStatusById(Long id, String statusRaw) {
        UserStatus status = UserStatus.parseUserStatus(statusRaw);
        findById(id).setStatus(status);
    }

    @Override
    public UserResult getById(Long id) {
        return userMapper.toDTO(findById(id));
    }


}
