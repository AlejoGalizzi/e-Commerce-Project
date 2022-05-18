package user.com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import user.com.ecommerce.model.entity.Country;

@RepositoryRestResource(collectionResourceRel = "countries", path = "countries")
public interface ICountryRepository extends JpaRepository<Country, Long> {

}
