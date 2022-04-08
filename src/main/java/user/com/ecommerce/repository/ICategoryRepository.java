package user.com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import user.com.ecommerce.model.entity.Category;

public interface ICategoryRepository extends JpaRepository<Category,Long> {

}
