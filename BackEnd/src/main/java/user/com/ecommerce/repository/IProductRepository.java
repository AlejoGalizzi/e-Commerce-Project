package user.com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import user.com.ecommerce.model.entity.Product;

@CrossOrigin("http://localhost:4200")
public interface IProductRepository extends JpaRepository<Product, Long> {

}
