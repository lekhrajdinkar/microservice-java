package microservice.courseApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import microservice.courseApp.repository.entity.Instructor;
import microservice.courseApp.services.InstructorSrv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseApp/Instructor")
@Tag( name = "Instructor", description = "Instructor API :: CRUD Operations")
public class InstructorController
{
    Logger logger = LoggerFactory.getLogger(InstructorController.class);
    
    @Autowired
    InstructorSrv srv;

    //@Override
    @GetMapping("/find-all")
    public List<Instructor> findAll() {
        logger.debug("CourseController :: findAll");
        return srv.findAll();
    }

    //@Override
    @GetMapping("/findById/{id}")
    public Instructor findById(@PathVariable Long id) {
        logger.debug("CourseController :: findById");
        return srv.findById(id);
    }

    //@Override
    @PostMapping("/save")
    public Long save(@RequestBody Instructor model) {
        logger.debug("CourseController :: save");
        return srv.save(model);
    }
}
