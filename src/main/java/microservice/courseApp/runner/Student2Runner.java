package microservice.courseApp.runner;

import microservice.courseApp.custom.Print;
import microservice.courseApp.custom.Student2CustomRepository;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
public class Student2Runner implements CommandLineRunner
{
    @Autowired
    Student2CustomRepository StudentRepo;

    @Override
    public void run(String... args) throws Exception
    {
        // ================
        // CRUD Operations
        // ================
        Print.print(
                "✔️ Student2Runner :: CRUD Operations :: CREATE",
                StudentRepo.addStudent("Lekhraj-Dinkar"),
                StudentRepo.addStudent("Manisha-Prasad")
        );

        Print.print(
                "✔️ Student2Runner :: CRUD Operations :: READ",
                StudentRepo.getStudent("Lekhraj-Dinkar")
        );

        Print.print(
                "✔️ Student2Runner :: CRUD Operations :: READ (Tuple)"
        );
        List<Tuple> studentTuples  = StudentRepo.getAllStudent_tuple();
        for(Tuple t : studentTuples){
           log.info(" tuple - {}, name-{}, bod-{}, gender-{}"
                   ,t, t.get(0), t.get(1), t.get("gender")); // ordinal/index based and alias based ◀️◀️
            // "select s.name, s.birthDate as dob, s.gender as gender from Student s"
            // notice alias : s.gender as gender
        }
    }
}
