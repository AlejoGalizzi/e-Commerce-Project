package user.com.ecommerce.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import user.com.ecommerce.model.entity.Customer;


public interface ICustomerRepository extends JpaRepository<Customer,Long> {

  Customer findByEmail(String email);
}
