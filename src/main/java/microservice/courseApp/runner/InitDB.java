package microservice.courseApp.runner;

import lombok.extern.slf4j.Slf4j;
import microservice.courseApp.repository.CategoryDAO;
import microservice.courseApp.repository.CourseDAO;
import microservice.courseApp.repository.InstructorDAO;
import microservice.courseApp.repository.StudentDAO;
import microservice.courseApp.repository.entity.Category;
import microservice.courseApp.repository.entity.Course;
import microservice.courseApp.repository.entity.Instructor;
import microservice.courseApp.repository.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class InitDB implements CommandLineRunner {

    @Autowired CategoryDAO categoryDAO ;
    @Autowired CourseDAO courseDAO ;
    @Autowired StudentDAO studentDAO ;
    @Autowired InstructorDAO instDao;

    @Override
    public void run(String... args) throws Exception {
        log.info("--- InitDB ---START");
        LoadDB();
        log.info("--- InitDB ---END");
    }

    void LoadDB()
    {

        // Instructor
        log.info(" ✔️ create Instructors");

            Instructor i1 = Instructor.builder().name("Lekhraj Dinkar").email("ld@ggg.com").id(1L).build();
            Instructor i2 = Instructor.builder().name("Manisha prasad").email("mp@ggg.com").id(2L).build();

        // Course
        log.info(" ✔️ create Courses");

            Course c1 = new Course(); c1.setId(1L);
                c1.setTitle("JavaScript MasterClass");
                c1.setDesc("Learn JavaScript with modern ES2021");
                c1.setInstructor(i1); // 1-1 ◀️

            Course c2 = new Course(); c2.setId(2L);
                c2.setTitle("python MasterClass");
                c2.setDesc("Learn web development with python");
                c2.setInstructor(i1); // 1-1 ◀️

            Course c3 = new Course(); c3.setId(3L);
                c3.setTitle("procreate MasterClass");
                c3.setDesc("Learn procreate App");
                c3.setInstructor(i2);  // 1-1 ◀️

        // Category
        log.info(" ✔️ create Categories");

            Category cat1 = new Category(1L, "Programming", "Learn leading Programming languages and Tools",
                    List.of(c1,c2)); // 1-M ◀️
            Category cat2 = new Category(2L, "Art", "Excel yourself in Art",
                    List.of(c3)); // 1-M ◀️

        // Student
        log.info(" ✔️ create Students");

            Student s1 = Student.builder()
                    .name("Shunkai chao")
                    .email("ssss@ggg.com")
                    .courses(courseDAO.findAll()) // M-M ◀️
                    .build();

            Student s2 = Student.builder()
                    .name("Arush prasad")
                    .email("aaaa@ggg.com")
                    .courses(List.of(courseDAO.findById(1L).get())) // M-M ◀️
                    .build();



            // =============== Save to DB ===============
            studentDAO.save(s1);
            studentDAO.save(s2);
    }
}


