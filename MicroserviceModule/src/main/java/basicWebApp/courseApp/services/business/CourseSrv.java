package basicWebApp.courseApp.services.business;

import basicWebApp.courseApp.repository.dto.CourseDTO;
import basicWebApp.courseApp.repository.entity.Course;
import basicWebApp.courseApp.services.BaseSrv;

import java.util.List;

public interface CourseSrv  extends BaseSrv<Course, Long> {
    public List<CourseDTO> findAllDto();
}
