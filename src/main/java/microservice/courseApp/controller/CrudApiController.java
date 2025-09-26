package microservice.courseApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import microservice.courseApp.custom.Print;
import microservice.courseApp.custom.Student2CustomRepository;
import microservice.courseApp.repository.dto.CourseDTO;
import microservice.courseApp.repository.entity.Category;
import microservice.courseApp.repository.entity.Course;
import microservice.courseApp.repository.entity.Instructor;
import microservice.courseApp.repository.entity.Student;
import microservice.courseApp.repository.modelMapper.CourseMapper;
import microservice.courseApp.services.business.CategorySrv;
import microservice.courseApp.services.business.CourseSrv;
import microservice.courseApp.services.business.InstructorSrv;
import microservice.courseApp.services.business.StudentSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
public class CrudApiController implements CrudApi
{
    @Autowired CategorySrv srv_Category;
    @Autowired  CourseSrv srv_Course;
    @Autowired  InstructorSrv srv_Instructor;
    @Autowired    StudentSrv studentSrv;
    @Autowired    Student2CustomRepository StudentCustomRepo;

    /*
      [Category] 1---* [Course] *---* [Student]
                       |
                       1
                       |
                  [Instructor]
     */

    // ============
    // ▶️ Courses
    // ===========
    @GetMapping("/Courses/find-all")
    private List<Course> findAll_Course() {
        List<Course>  res = srv_Course.findAll();
        res.stream().forEach(System.out::print);
        return srv_Course.findAll();
    }

    @GetMapping("/Courses/find-all-dto")
    private List<CourseDTO> findAllDto_Course() {        return srv_Course.findAllDto();    }

    @GetMapping("/Courses/find-by-id")
    private Course findById_Course(Long id) {        return srv_Course.findById(id);    }

    @PostMapping("/Courses/save")
    @Operation( summary = "Create/Save Course", description = "dto2Model :: CourseDTO >> Course entity")
    private Long save_Course(@RequestBody CourseDTO dto) {
        Course c = CourseMapper.dto2Model(dto); //manual mapper, Todo: use mapStruct
        c.setCategory(srv_Category.findById(dto.getCategoryId())); // loading Category Entity manually from id.
        c.setInstructor(srv_Instructor.findById(dto.getInstructorId()));
        return srv_Course.save(c);
    }

    // ============================================
    // Category :: Implements CrideApi interface  👈🏻👈🏻
    // ============================================
    @Override    public List<Category> findAll_Category() { return srv_Category.findAll(); }
    @Override    public Category findById_Category(Long id) { return srv_Category.findById(id); }
    @Override    public Long save_Category(Category category) { return srv_Category.save(category); }

    // ===========
    // Instructor
    // ===========
    @GetMapping("/find-all")
    public List<Instructor> findAll_Instructor() {return srv_Instructor.findAll();    }

    @GetMapping("/find-by-id/{id}")
    public Instructor findById_Instructor(@PathVariable Long id) {return srv_Instructor.findById(id);    }

    @PostMapping("/save")
    public Long save_Instructor(@RequestBody Instructor model) {return srv_Instructor.save(model);    }

    // ===========
    // Student
    // ===========
    @GetMapping("/student/find-all")
    public List<Student> findAll_Student() {        return studentSrv.findAll();    }

    @GetMapping("/student/find-by-id/{id}")
    public Student findById_Student(@PathVariable Long id) {      return studentSrv.findById(id);    }

    @PostMapping("/student/save")
    public Long save_Student(@RequestBody Student model) {      return studentSrv.save(model);    }

    // ========================================================
    // ▶️Student2 :: CRUD Operations with Custom repo
    // ========================================================
    @GetMapping("/student2/custom-repo/crud-operations")
    @Operation( summary = "Custom Repo :: CRUD Operations", description = "using EntityManager and NamedQueries")
    public String CrudOperationsWwithCustomRepo() throws Exception
    {

        Print.print(
                "✔️ Student2 :: CRUD Operations :: CREATE",
                StudentCustomRepo.addStudent("Lekhraj-Dinkar"),
                StudentCustomRepo.addStudent("Manisha-Prasad")
        );

        Print.print(
                "✔️ Student2 :: CRUD Operations :: READ",
                StudentCustomRepo.getStudent("Lekhraj-Dinkar")
        );

        Print.print(
                "✔️ Student2 :: CRUD Operations :: READ (Tuple)"
        );
        List<Tuple> studentTuples  = StudentCustomRepo.getAllStudent_tuple();
        for(Tuple t : studentTuples){
            log.info(" tuple - {}, name-{}, bod-{}, gender-{}"
                    ,t, t.get(0), t.get(1), t.get("gender")); // ordinal/index based and alias based ◀️◀️
            // "select s.name, s.birthDate as dob, s.gender as gender from Student s"
            // notice alias : s.gender as gender
        }

        return "✔️ Student2 :: CRUD Operations :: done.";
    }
}
