package user.com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import user.com.ecommerce.model.entity.Customer;


public interface ICustomerRepository extends JpaRepository<Customer,Long> {

}
