package vn.ohana.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ohana.category.CategoryRepository;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Long count() {
        return categoryRepository.count();
    }
}
