package vn.ohana.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ohana.StatusUtility;
import vn.ohana.Utility;
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
    public void show(Long utilityId) {
        Optional<Utility> optionalUtility = utilityRepository.findById(utilityId);
        if (optionalUtility.isPresent()) {
            Utility utility = optionalUtility.get();
            utility.setStatus(StatusUtility.SHOW);
            utilityRepository.save(utility);
        }
    }

    @Override
    public void hidden(Long utilityId) {
        Optional<Utility> optionalUtility = utilityRepository.findById(utilityId);
        if (optionalUtility.isPresent()) {
            Utility utility = optionalUtility.get();
            utility.setStatus(StatusUtility.HIDDEN);
            utilityRepository.save(utility);
        }

    }

    @Override
    public List<UtilityResult> findAllById(Iterable<Long> longs) {
        List<Utility> entities = utilityRepository.findAllById(longs);
        return utilityMapper.toDTOList(entities);
    }

    @Override
    public List<UtilityResult> findAllByStatus(StatusUtility status) {
        List<Utility> entities = utilityRepository.findAllByStatus(status);
        return utilityMapper.toDTOList(entities);
    }

    @Override
    public List<UtilityResult> findAllByIdASC(List<Long> utilityIds) {
        return utilityRepository.findAllById(utilityIds).
                stream()
                .sorted(Comparator.comparingInt(Utility::getPriority))
                .map(u -> utilityMapper.toDTO(u))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public UtilityResult update(UpdateUtilityParam param) {
        return null;
    }
}
