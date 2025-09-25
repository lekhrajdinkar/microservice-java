package microservice.courseApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import microservice.courseApp.errorHandling.MyException;
import microservice.courseApp.repository.CourseDAO;
import microservice.courseApp.repository.dto.CourseDTO;
import microservice.courseApp.repository.mapper.CourseMapper;
import microservice.courseApp.repository.entity.Course;
import microservice.courseApp.services.CategorySrv;
import microservice.courseApp.services.CourseSrv;
import microservice.courseApp.services.InstructorSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/courseApp/Course")
@Tag( name = "Course", description = "Course API :: CRUD Operations")
public class CourseController 
{
    @Autowired CourseSrv srv;
    @Autowired CategorySrv categorySrv;
    @Autowired InstructorSrv instSrv;

    // ============
    // ▶️ CRUD
    // ===========
    @GetMapping("/find-all")
    private List<Course> findAll() {
        log.debug("CourseController :: findAll");
        List<Course>  res = srv.findAll();
        res.stream().forEach(System.out::print);
        return srv.findAll();
    }

    @GetMapping("/find-all-dto")
    private List<CourseDTO> findAllDto() {
        log.debug("CourseController :: findAllDto");
        return srv.findAllDto();
    }

    @GetMapping("/findById")
    private Course findById(Long id) {
        log.debug("CourseController :: findById");
        return srv.findById(id);
    }

    @PostMapping("/save")
    @Operation( summary = "Create/Save Course", description = "dto2Model :: CourseDTO >> Course entity")
    private Long save(@RequestBody CourseDTO dto) {
        log.debug("CourseController :: save");

            Course c = CourseMapper.dto2Model(dto); //manual mapper, Todo: use mapStruct
            c.setCategory(categorySrv.findById(dto.getCategoryId())); // loading Category Entity manually from id.
            c.setInstructor(instSrv.findById(dto.getInstructorId()));

        return srv.save(c);
    }

    // ============
    // @Query
    // ===========

    @Autowired CourseDAO dao;

    @GetMapping(
            value="/q-find-by-cat",
            produces="application/v1+json"
    )
    private List<CourseDTO> QFindByCategory() {
        log.debug("CourseController :: QFindByCategory");
        //List<Course>  res =  dao.find2ByCategoryId(1L).get();
        List<Course>  res =  dao.find3ByCategoryId(1L);
        return res.stream()
                .map((Course x )-> CourseMapper.model2Dto(x))
                .collect(Collectors.toList());
    }

    @GetMapping("/q-find-by-inst")
    private List<CourseDTO> QFindByInstructor() {
        log.debug("CourseController :: QFindByInstructor");
        //List<Course>  res =  dao.find2ByCategoryId(1L).get();
        List<Course>  res =  dao.find1ByInstructorId(1L);
        return res.stream()
                .map((Course x )-> CourseMapper.model2Dto(x))
                .collect(Collectors.toList());
    }

    @GetMapping("/q-find-All-page/{page}/{size}")
    private List<CourseDTO> QFindAllPage(@PathVariable int page, @PathVariable int size) {
        log.debug("CourseController :: QFindAllPage");
        Page<Course> res = dao.findAllWithPagination( PageRequest.of(page, size, Sort.by("title").descending().and(Sort.by("desc"))));
        return res
                .getContent()
                .stream()
                .map((Course x )-> CourseMapper.model2Dto(x))
                .collect(Collectors.toList());
    }

    // ============ Exception ===========

    @RequestMapping( method = RequestMethod.GET, value = "/exp")
    private ResponseEntity<String> testException()
    {
        return ResponseEntity.badRequest().body("Exception testing - body");
    }

    @RequestMapping( method = RequestMethod.GET, value = "/exp/control-advise")
    private ResponseEntity<String> testException2()
    throws Exception
    {
        throw new MyException("Exception testing - body");
    }

    @RequestMapping( method = RequestMethod.GET, value = "/exp/rs-exp")
    @ResponseStatus(value = HttpStatus.BAD_REQUEST) //status 1 - Default response
    private ResponseEntity<String> testException3()
    throws ResponseStatusException
    {
        throw new  ResponseStatusException(HttpStatus.FORBIDDEN, " Spring 5 - Response Status Exception"); //status 2 - forbidden
    }
}
