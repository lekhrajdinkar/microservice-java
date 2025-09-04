package more.database.repo;

import com.lekhraj.java.spring.database.entities.*;
import more.database.entities.AppOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Transactional(transactionManager = "transactionManager_for_postgres")
@Repository
public interface AppOrderRepository extends JpaRepository<AppOrder, Long> {
}
