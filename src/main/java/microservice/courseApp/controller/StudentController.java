package microservice.courseApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import microservice.courseApp.custom.Print;
import microservice.courseApp.custom.Student2CustomRepository;
import microservice.courseApp.repository.entity.Student;
import microservice.courseApp.services.StudentSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/courseApp/Student")
@Tag( name = "Student and Student2(customRepoDemo)", description = "Student API :: CRUD Operations")
public class StudentController
{
    @Autowired StudentSrv srv;
    @Autowired Student2CustomRepository StudentCustomRepo;

    // ================
    // ▶️CRUD Operations
    // ================
    @GetMapping("/student/find-all")
    public List<Student> findAll() {
        log.debug("StudentController :: findAll");
        return srv.findAll();
    }

    @GetMapping("/student/findById/{id}")
    public Student findById(@PathVariable Long id) {
        log.debug("StudentController :: findById");
        return srv.findById(id);
    }

    @PostMapping("/student/save")
    public Long save(@RequestBody Student model) {
        log.debug("StudentController :: save");
        return srv.save(model);
    }

    // ================================
    // ▶️CRUD Operations with Custom repo
    // ================================
    @GetMapping("/student2/custom-repo/crud-operations")
    @Operation( summary = "Custom Repo :: CRUD Operations", description = "using EntityManager and NamedQueries")
    public String CrudOperationsWwithCustomRepo() throws Exception
    {

        Print.print(
                "✔️ Student2Runner :: CRUD Operations :: CREATE",
                StudentCustomRepo.addStudent("Lekhraj-Dinkar"),
                StudentCustomRepo.addStudent("Manisha-Prasad")
        );

        Print.print(
                "✔️ Student2Runner :: CRUD Operations :: READ",
                StudentCustomRepo.getStudent("Lekhraj-Dinkar")
        );

        Print.print(
                "✔️ Student2Runner :: CRUD Operations :: READ (Tuple)"
        );
        List<Tuple> studentTuples  = StudentCustomRepo.getAllStudent_tuple();
        for(Tuple t : studentTuples){
            log.info(" tuple - {}, name-{}, bod-{}, gender-{}"
                    ,t, t.get(0), t.get(1), t.get("gender")); // ordinal/index based and alias based ◀️◀️
            // "select s.name, s.birthDate as dob, s.gender as gender from Student s"
            // notice alias : s.gender as gender
        }

        return "✔️ Student2Runner :: CRUD Operations :: done.";
    }
}
