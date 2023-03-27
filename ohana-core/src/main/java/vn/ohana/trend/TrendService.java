package vn.ohana.trend;

import vn.ohana.entities.Trend;

import java.util.List;

public interface TrendService {


    List<Trend> getAllTrend();

    Trend getById( Long id);

    void update( Trend trend);

    Trend findByNameIsLike(String location);
}
