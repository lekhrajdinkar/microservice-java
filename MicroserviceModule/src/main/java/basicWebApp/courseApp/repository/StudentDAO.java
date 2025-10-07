package basicWebApp.courseApp.repository;

import microservice.basicWebApp.courseApp.repository.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDAO extends JpaRepository<Student,Long> {

}

