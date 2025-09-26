package microservice.basicWebApp.courseApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import microservice.basicWebApp.courseApp.repository.CourseDAO;
import microservice.basicWebApp.courseApp.repository.dto.CourseDTO;
import microservice.basicWebApp.courseApp.repository.modelMapper.CourseMapper;
import microservice.basicWebApp.courseApp.repository.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/query/Course")
@Tag( name = "Queries on Course", description = "HQL, JPQL, SQL, NamedQueries, mapper(entity2dto), etc")
public class QueryController
{
    @Autowired CourseDAO dao;

    @GetMapping(
            value="/find-by-cat",
            produces="application/v1+json"
    )
    @Operation(description = "returns List<CourseDTO> not List<Course> 👈🏻👈🏻")
    private List<CourseDTO> QFindByCategory() {
        log.debug("CourseController :: QFindByCategory");
        //List<Course>  res =  dao.find2ByCategoryId(1L).get();
        List<Course>  res =  dao.find3ByCategoryId(1L);
        return res.stream()
                .map((Course x )-> CourseMapper.model2Dto(x))
                .collect(Collectors.toList());
    }

    @GetMapping("/find-by-inst")
    private List<CourseDTO> QFindByInstructor() {
        log.debug("CourseController :: QFindByInstructor");
        //List<Course>  res =  dao.find2ByCategoryId(1L).get();
        List<Course>  res =  dao.find1ByInstructorId(1L);
        return res.stream()
                .map((Course x )-> CourseMapper.model2Dto(x))
                .collect(Collectors.toList());
    }

    @Operation(description = "pagination Demo 👈🏻👈🏻")
    @GetMapping("/pagination/get-course/{page}/{size}")
    private List<CourseDTO> QFindAllPage(@PathVariable int page, @PathVariable int size) {
        log.debug("CourseController :: QFindAllPage");
        Page<Course> res = dao.findAllWithPagination( PageRequest.of(page, size, Sort.by("title").descending().and(Sort.by("desc"))));
        return res
                .getContent()
                .stream()
                .map((Course x )-> CourseMapper.model2Dto(x))
                .collect(Collectors.toList());
    }

}
