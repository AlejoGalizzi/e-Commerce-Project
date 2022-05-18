package user.com.ecommerce.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import user.com.ecommerce.model.entity.State;

@RepositoryRestResource(collectionResourceRel = "states", path = "states")
public interface IStateRepository extends JpaRepository<State, Long> {
  List<State> findByCountryCode(@Param("code") String code);
}
