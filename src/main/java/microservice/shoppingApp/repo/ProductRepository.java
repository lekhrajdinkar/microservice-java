package microservice.shoppingApp.repo;


import microservice.shoppingApp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Transactional(transactionManager = "transactionManager_for_postgres")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

