package vn.ohana.trend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ohana.Trend;

import java.util.List;

@Service
public class TrendServiceImpl implements TrendService {

    @Autowired
    TrendRepository trendRepository;

    @Override
    public List<Trend> getAllTrend() {
        return trendRepository.getTop6();
    }

    @Override
    public Trend getById(Long id) {
        return trendRepository.getById(id);
    }

    @Override
    public void update(Trend trend) {
        trendRepository.save(trend);
    }

    @Override
    public Trend findByNameIsLike(String location) {
        return trendRepository.getByName(location);
    }
}
