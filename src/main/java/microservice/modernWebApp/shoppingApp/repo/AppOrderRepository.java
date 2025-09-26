package microservice.modernWebApp.shoppingApp.repo;

import microservice.modernWebApp.shoppingApp.entities.AppOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Transactional(transactionManager = "transactionManager_for_postgres")
@Repository
public interface AppOrderRepository extends JpaRepository<AppOrder, Long> {
}
