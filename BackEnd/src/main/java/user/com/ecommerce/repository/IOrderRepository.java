package user.com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import user.com.ecommerce.model.entity.Order;

public interface IOrderRepository extends JpaRepository<Order,Long> {

}
