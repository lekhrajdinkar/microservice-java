package microservice.courseApp.services.business;

import microservice.courseApp.repository.dto.CourseDTO;
import microservice.courseApp.repository.entity.Course;
import microservice.courseApp.services.BaseSrv;

import java.util.List;

public interface CourseSrv  extends BaseSrv<Course, Long> {
    public List<CourseDTO> findAllDto();
}
