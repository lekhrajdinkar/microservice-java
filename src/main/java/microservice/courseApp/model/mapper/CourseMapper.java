package microservice.courseApp.model.mapper;

import microservice.courseApp.model.dto.CourseDTO;
import microservice.courseApp.model.entity.Course;

public class CourseMapper {
    public static CourseDTO model2Dto(Course model){
        return CourseDTO.builder()
                .desc(model.getDesc())
                .id(model.getId())
                .title(model.getTitle())
                .instructorId(model.getInstructor().getId())
                .categoryId(model.getCategory().getId())
                .build();
    }

    public static Course dto2Model(CourseDTO dto){
        return Course.builder()
                .desc(dto.getDesc())
                //.id(dto.getId())
                .title(dto.getTitle())
                //.category(dto)
                .build();
    }
}
