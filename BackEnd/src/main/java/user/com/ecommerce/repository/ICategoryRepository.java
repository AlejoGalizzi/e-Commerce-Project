package user.com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import user.com.ecommerce.model.entity.Category;

@RepositoryRestResource(collectionResourceRel = "category",path = "product-category")
@CrossOrigin("http://localhost:4200")
public interface ICategoryRepository extends JpaRepository<Category,Long> {

}
