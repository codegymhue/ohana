package vn.ohana.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.*;
import vn.ohana.location.LocationMapper;
import vn.ohana.entities.Post;
import vn.ohana.entities.StatusPost;
import vn.ohana.entities.User;
import vn.ohana.mail.MailService;
import vn.ohana.post.dto.*;
import vn.ohana.report.dto.DateReportResult;
import vn.ohana.user.UserMapper;
import vn.ohana.user.UserService;
import vn.ohana.user.UserRepository;

import vn.ohana.user.dto.UserUpdateParam;
import vn.ohana.utility.UtilityService;
import vn.ohana.utility.dto.UtilityResult;
import vn.rananu.shared.exceptions.NotFoundException;
import vn.rananu.shared.exceptions.OperationException;
import vn.rananu.shared.exceptions.ValidationException;

import java.math.BigInteger;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;



@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired

    private RentHouseMapper rentHouseMapper;

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UtilityService utilityService;

    @Autowired
    MailService mailService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostFilterRepository postFilterRepository;

    @Autowired
    private RentHouseRepository rentHouseRepository;

    @Autowired
    private PostMediaRepository postMediaRepository;

    @Autowired
    public JavaMailSender emailSender;


    @Override
    @Transactional(readOnly = true)
    public Page<PostResult> findAll(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        return toDtoPage(page);
    }

    @Override
    @Transactional
    public Map<String,Object> modifyStatusByIds(Set<Long> ids, String status) {

        StatusPost statusPost = StatusPost.parseStatusPosts(status);

        Map<String, Object> result = new HashMap<>();

        List<Long> successIds = new ArrayList<>();
        Map<String,Boolean> emailStatus = new HashMap<>();
        List<Long> failIds = new ArrayList<>();

        Iterable<Post> entities = postRepository.findAllById(ids);
        entities.forEach(entity -> {
            switch (status) {
                case "PUBLISHED": {
                    try {
                        entity.setStatus(statusPost);
                        mailService.sendPostApprovedMail(entity.getUser().getEmail(),entity.getTitle());
                        emailStatus.put(entity.getUser().getEmail(), Boolean.TRUE);
                        successIds.add(entity.getId());
                    } catch (InterruptedException e) {
                        emailStatus.put(entity.getUser().getEmail(), Boolean.FALSE);
                    }
                    break;
                }
                case "REFUSED": {
                    try {
                        entity.setStatus(statusPost);
                        mailService.sendPostRefusedMail(entity.getUser().getEmail(),entity.getTitle());
                        emailStatus.put(entity.getUser().getEmail(), Boolean.TRUE);
                        successIds.add(entity.getId());
                    } catch (InterruptedException e) {
                        emailStatus.put(entity.getUser().getEmail(), Boolean.FALSE);
                    }
                    break;
                }
                default:
                    throw new ValidationException("post.status.invalidStatus");
            }

        });
        result.put("succeed", successIds);

        List<Long> entityIds = StreamSupport
                .stream(entities.spliterator(), false)
                .map(Post::getId)
                .collect(Collectors.toList());

        ids.forEach(id -> {
            if (!entityIds.contains(id)) {
                failIds.add((id));
            }
        });
        result.put("emailStatus", emailStatus);
        result.put("failed", failIds);
        return result;
    }

    public Page<PostResult> insertUtilityResultList(Page<Post> page) {
        List<Post> entities = page.getContent();


        Set<Integer> utilityIds = entities.stream().map(Post::getUtilities)
                .flatMap(Set::stream).collect(Collectors.toSet());


        return page.map(entity -> addPostResultUtilities(entity, utilityIds));
    }


    public Map<Long, String> modifyStatusPostByIds(Set<Long> ids, String statusPost) {
        StatusPost status = StatusPost.parseStatusPosts(statusPost);
        Map<Long, String> result = new HashMap<>();
        Iterable<Post> entities = postRepository.findAllById(ids);
        entities.forEach(entity -> {
            entity.setStatus(status);
            result.put(entity.getId(), "successful");
        });

        List<Long> entityIds = StreamSupport.stream(entities.spliterator(), false).map(Post::getId).collect(Collectors.toList());
        ids.forEach(id -> {
            if (!entityIds.contains(id))
                result.put(id, "failed");
        });
        return result;
    }




    @Override
    public Page<PostResult> findAllByUser(UserUpdateParam user, Pageable pageable) {

        Page<Post> post = postRepository.findByUser(userMapper.toEntity(user), pageable);

        return toDtoPage(post);
    }

    @Override
    public Page<PostResult> filterPublishedPosts(PostFilterParam filter, Pageable pageable) {
        filter.setStatus(StatusPost.PUBLISHED);
        return toDtoPage(postFilterRepository.findAllByFilters(filter, pageable));
    }

    @Override
    public PostResult getById(Long pId) {
        Post post = findById(pId);
        return addPostResultUtilities(post, post.getUtilities());
    }

    @Override
    @Transactional
    public PostResult updateStatusById(PostUpdateParam postUpdateParam) {
        Post post = findById(postUpdateParam.getId());
        postMapper.transferFields(postUpdateParam, post, true);
        try {
            if (postUpdateParam.getStatus().equals(StatusPost.PUBLISHED)) {
                mailService.sendPostApprovedMail(post.getUser().getEmail(), post.getTitle());
            }
            if (postUpdateParam.getStatus().equals(StatusPost.REFUSED)) {
                mailService.sendPostRefusedMail(post.getUser().getEmail(), post.getTitle());
            }
        } catch (InterruptedException e) {
            throw new OperationException("mai.exception.sendError");
        }
        return addPostResultUtilities(post, post.getUtilities());
    }

    @Override
    public Page<PostResult> getTop10PostsNew(Long id) {

        PostFilterParam filterParam = new PostFilterParam();
        filterParam.setStatus(StatusPost.PUBLISHED);
        Pageable pageable = PageRequest.of(0, 10,Sort.by("createdAt").descending());
        Location location = new Location();
        location.setProvinceId(id);
        filterParam.setLocationFilter(location);

        return toDtoPage(postFilterRepository.findAllByFilters(filterParam, pageable));
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("post.exception.notFound"));
    }


    @Override
    public Page<PostResult> findAllByStatusAndUser(StatusPost status, Long id, Pageable pageable) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("user.exception.notFound"));
        return toDtoPage(postRepository.findAllByStatusAndUser(status, user,pageable));
    }

    @Override
    public Page<PostResult> findAllByStatus(StatusPost statusPost, Pageable pageRequest) {
        return toDtoPage(postRepository.findAllByStatus(statusPost, pageRequest));
    }

    @Override
    public Long count() {
        return postRepository.count();
    }

    @Override
    public Long countPostByStatus(StatusPost status) {
        return postRepository.countPostByStatus(status);
    }

    @Override
    @Transactional(readOnly = false)
    public List<Object> countByMonthBetweenDate(Instant startDate, Instant endDate) {
        return postRepository.countByMonthBetweenDate(startDate, endDate);
    }


    @Override
    public Page<PostResult> filter(PostFilterParam filter, Pageable pageable) {
        Page<Post> page = postFilterRepository.findAllByFilters(filter, pageable);
        return toDtoPage(page);
    }

    private Page<PostResult> toDtoPage(Page<Post> page) {
        return page.map(entity -> postMapper.toDTO(entity));
    }

    private PostResult addPostResultUtilities(Post entity, Set<Integer> utilityIds) {
        List<UtilityResult> utilities = utilityService.findAllByIds(utilityIds);
        PostResult dto = postMapper.toDTO(entity);
        List<UtilityResult> newUtilities = utilities
                .stream()
                .filter(utility ->
                        entity.getUtilities()
                                .stream()
                                .anyMatch(id -> utility.getId().equals(id)))
                .collect(Collectors.toList());
        dto.setUtilities(newUtilities);
        return dto;
    }

    @Override
    @Transactional
    public PostCreateParam save(PostCreateParam postCreateParam) {

        long sizeUtilities = (postCreateParam.getUtilities()).size();
        long sizeImages = (postCreateParam.getImages()).size();

        if (sizeUtilities < 2) {
            throw new ValidationException("Tiện ích không được dưới 2");
        }

        if (postCreateParam.getThumbnailId() == null) {
            throw new ValidationException("Ảnh đại diện không được để trống");
        }
        if (sizeImages <5 || sizeImages >15) {
            throw new ValidationException("Chỉ chấp nhận từ 5 đến 15 hình ảnh");
        }

        User user = userService.findById(postCreateParam.getIdUser());
        Post post = postMapper.toPost(postCreateParam);

        RentHouse rentHouse = rentHouseMapper.toRentHouse(postCreateParam.getRentHouse());

        post.setStatus(StatusPost.PENDING_REVIEW);
        post.setUser(user);
        rentHouseRepository.save(rentHouse);
        post.setRentHouse(rentHouse);

        postRepository.save(post);
        String url = "https://res.cloudinary.com/dh8nlcoul/image/upload/l_l_cloudinary_icon,c_limit,w_200,h_200,o_60/ivzmrhhwlcphwev4jx19.jpg";
        for (PostMediaParam img : postCreateParam.getImages()) {
            PostMedia postMedia = new PostMedia();
            postMedia.setPublicId(img.getPublicId());
            postMedia.setFileUrl("https://res.cloudinary.com/dh8nlcoul/image/upload/l_l_cloudinary_icon,c_limit,w_200,h_200,o_60/" + img.getPublicId() + ".jpg");
            postMedia.setPost(post);
            postMediaRepository.save(postMedia);

        }

        return postCreateParam;
    }

}


