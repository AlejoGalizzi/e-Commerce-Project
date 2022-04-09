package user.com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import user.com.ecommerce.model.entity.Product;

public interface IProductRepository extends JpaRepository<Product,Long> {
}
