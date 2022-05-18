package user.com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import user.com.ecommerce.model.entity.Category;

@RepositoryRestResource(collectionResourceRel = "categories", path = "product-categories")
public interface ICategoryRepository extends JpaRepository<Category, Long> {

}
