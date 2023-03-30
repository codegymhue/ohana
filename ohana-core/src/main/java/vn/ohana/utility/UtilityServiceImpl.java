package vn.ohana.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ohana.entities.StatusUtility;
import vn.ohana.entities.Utility;
import vn.ohana.utility.dto.UpdateUtilityParam;
import vn.ohana.utility.dto.UtilityResult;
import vn.rananu.shared.exceptions.NotFoundException;

import java.util.List;
import java.util.Set;

@Service
public class UtilityServiceImpl implements UtilityService {

    @Autowired
    private UtilityRepository utilityRepository;

    @Autowired
    private UtilityMapper utilityMapper;


    @Override
    public List<UtilityResult> findAllByIds(Set<Long> ids) {
        List<Utility> utilities = utilityRepository.findAllByIdIn(ids);
        return utilityMapper.toDTOList(utilities);
    }

    @Override
    public Utility findById(Long id) {
        return utilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("utility.exception.notFound"));
    }

    @Override
    public UtilityResult getById(Long id) {
        return utilityMapper.toDTO(findById(id));
    }


    @Override
    public List<UtilityResult> findAllByStatus(StatusUtility status) {
        List<Utility> entities = utilityRepository.findAllByStatus(status);
        return utilityMapper.toDTOList(entities);
    }


    @Override
    public void deleteById(Long id) {

    }

    @Override
    public UtilityResult update(UpdateUtilityParam param) {
        return null;
    }
}
