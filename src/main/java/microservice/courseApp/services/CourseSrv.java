package microservice.courseApp.services;

import microservice.courseApp.model.dto.CourseDTO;
import microservice.courseApp.model.entity.Course;

import java.util.List;

public interface CourseSrv  extends BaseSrv<Course, Long>{
    public List<CourseDTO> findAllDto();
}
