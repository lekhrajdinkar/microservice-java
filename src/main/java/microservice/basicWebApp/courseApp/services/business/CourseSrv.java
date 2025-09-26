package microservice.basicWebApp.courseApp.services.business;

import microservice.basicWebApp.courseApp.repository.dto.CourseDTO;
import microservice.basicWebApp.courseApp.repository.entity.Course;
import microservice.basicWebApp.courseApp.services.BaseSrv;

import java.util.List;

public interface CourseSrv  extends BaseSrv<Course, Long> {
    public List<CourseDTO> findAllDto();
}
