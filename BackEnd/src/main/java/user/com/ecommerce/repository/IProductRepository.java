package user.com.ecommerce.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import user.com.ecommerce.model.entity.Product;

@CrossOrigin("http://localhost:4200")
public interface IProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findByCategoryId(@RequestParam("id") Long id, Pageable pageable);
}
