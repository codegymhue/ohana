package vn.ohana.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ohana.StatusUtility;
import vn.ohana.Utility;
import vn.ohana.utility.dto.UtilityResult;

import java.util.ArrayList;
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
    public Utility save(Utility utility) {
        return utilityRepository.save(utility);
    }



    @Override
    public void delete(Utility utilityId) {
        utilityRepository.delete(utilityId);

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
        return utilityRepository.findAllById(longs).
                stream()
                .map(u -> utilityMapper.toDTO(u))
                .collect(Collectors.toList());
    }

    @Override
    public List<UtilityResult> findAllByStatus(StatusUtility status) {
        List<UtilityResult> utilities = new ArrayList<>();
        utilityRepository.findAllByStatus(status).
            forEach(utility -> utilities.
            add(utilityMapper.
            toDTO(utility)));
        return utilities;
    }

    @Override
    public List<UtilityResult> findAllByIdASC(List<Long> utilityIds) {
        List<Utility> utilities = utilityRepository.findAllById(utilityIds);
        return utilities.
                stream()
                .sorted(Comparator.comparingInt(Utility::getPriority))
                .map(u -> utilityMapper.toDTO(u))
                .collect(Collectors.toList());
    }
}
