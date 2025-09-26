package microservice.basicWebApp.courseApp.repository;

import microservice.basicWebApp.courseApp.repository.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDAO extends JpaRepository<Category,Long> {


}

