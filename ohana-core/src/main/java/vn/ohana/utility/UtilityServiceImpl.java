package vn.ohana.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ohana.entities.Post;
import vn.ohana.entities.StatusUtility;
import vn.ohana.entities.Utility;
import vn.ohana.post.dto.PostResult;
import vn.ohana.utility.dto.CreateUtilityParam;
import vn.ohana.utility.dto.UpdateUtilityParam;
import vn.ohana.utility.dto.UtilityResult;
import vn.rananu.shared.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UtilityServiceImpl implements UtilityService {

    @Autowired
    private UtilityRepository utilityRepository;

    @Autowired
    private UtilityMapper utilityMapper;


    @Override
    @Transactional(readOnly = true)
    public List<UtilityResult> findAllByIds(Set<Integer> ids) {
        List<Utility> utilities = utilityRepository.findAllByIdIn(ids);
        return utilityMapper.toDTOList(utilities);
    }

    @Override
    public Utility findById(Integer id) {
        return utilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("utility.exception.notFound"));
    }

    @Override
    public UtilityResult getById(Integer id) {
        return utilityMapper.toDTO(findById(id));
    }

    @Override
    public UtilityResult save(CreateUtilityParam param) {
        param.setStatus(StatusUtility.SHOW);
        Utility utility = utilityMapper.toEntity(param);
        return utilityMapper.toDTO(utilityRepository.save(utility));
    }


    @Override
    public List<UtilityResult> findAllByStatus(StatusUtility status) {
        List<Utility> entities = utilityRepository.findAllByStatus(status);
        return utilityMapper.toDTOList(entities);
    }


    @Override
    public void deleteById(Integer id) {

    }

    @Override
    @Transactional
    public UtilityResult update(UpdateUtilityParam param) {
        Utility utility = utilityRepository.findById(param.getId())
                .orElseThrow(()-> new NotFoundException("utility.exception.notFound"));
        utilityMapper.transferFields(param, utility,true);
        return utilityMapper.toDTO(utility);
    }


    @Override
    public Page<UtilityResult> findAll(Pageable pageable) {
        Page<Utility> page = utilityRepository.findAll(pageable);
        return toDtoPage(page);
    }

    @Override
    public Page<UtilityResult> findAllByStatus(Pageable page, String status) {
        Page<Utility> utilityPage = utilityRepository.findByStatus(StatusUtility.parseStatusUtility(status), page);
        return toDtoPage(utilityPage);
    }

    @Override
    @Transactional
    public UtilityResult updateStatus(Integer utilityId, String status) {
        StatusUtility statusUtility = StatusUtility.parseStatusUtility(status);
        Utility utility = utilityRepository.findById(utilityId).orElseThrow(() -> new NotFoundException("utility.exception.notFound"));
        utility.setStatus(statusUtility);
        return utilityMapper.toDTO(utility);
    }

    private Page<UtilityResult> toDtoPage(Page<Utility> page) {
        return page.map(entity -> utilityMapper.toDTO(entity));
    }

}
