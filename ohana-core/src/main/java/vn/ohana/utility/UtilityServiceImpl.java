package vn.ohana.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public List<UtilityResult> findAllByStatus(StatusUtility status) {
        List<Utility> entities = utilityRepository.findAllByStatus(status);
        return utilityMapper.toDTOList(entities);
    }


    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public UtilityResult update(UpdateUtilityParam param) {
        return null;
    }

    @Override
    public List<UtilityResult> findAll() {
        return utilityMapper.toDTOList(utilityRepository.findAll());
    }
}
