package vn.ohana.report;

import vn.ohana.entities.StatusPost;
import vn.ohana.entities.UserStatus;

public interface ReportService {

    Long countCategory();

    Long countUser();

    Long countUtility();

    Long countPost();



    Long countUserByStatus(UserStatus status);

    Long countPostByStatus(StatusPost status);
}
