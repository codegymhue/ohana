package vn.ohana.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ohana.entities.StatusUtility;
import vn.ohana.entities.Utility;
import vn.ohana.utility.dto.UpdateUtilityParam;
import vn.ohana.utility.dto.UtilityResult;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtilityServiceImpl implements UtilityService {

    @Autowired
    UtilityRepository utilityRepository;

    @Autowired
    UtilityMapper utilityMapper;

    @Override
    public List<UtilityResult> findAll() {
        List<Utility> utilities = utilityRepository.findAll();
        return utilities.
                stream()
                .sorted(Comparator.comparingInt(Utility::getPriority))
                .map(u -> utilityMapper.toDTO(u))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UtilityResult> findById(Long id) {
        Optional<Utility> optionalUtility = utilityRepository.findById(id);
        return optionalUtility.map(utilities -> Optional.of(utilityMapper.toDTO(utilities))).orElse(null);
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
