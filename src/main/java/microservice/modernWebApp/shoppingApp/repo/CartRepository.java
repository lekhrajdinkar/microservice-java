package microservice.modernWebApp.shoppingApp.repo;

import microservice.modernWebApp.shoppingApp.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Transactional(transactionManager = "transactionManager_for_postgres")
@Repository
public interface CartRepository extends JpaRepository<Cart, Long>
{
    /*@Query("select cart from Cart c where c.c")
    Cart findByCustomer(Customer customer);*/
}
