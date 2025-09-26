package microservice.basicWebApp.courseApp.repository;

import microservice.basicWebApp.courseApp.repository.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorDAO extends JpaRepository<Instructor,Long> {
}

