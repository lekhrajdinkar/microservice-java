package modernWebApp.shoppingApp.repo;

import modernWebApp.shoppingApp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Transactional(transactionManager = "transactionManager_for_postgres")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
