package microservice.courseApp.services;

import microservice.courseApp.repository.dto.CourseDTO;
import microservice.courseApp.repository.entity.Course;

import java.util.List;

public interface CourseSrv  extends BaseSrv<Course, Long>{
    public List<CourseDTO> findAllDto();
}
