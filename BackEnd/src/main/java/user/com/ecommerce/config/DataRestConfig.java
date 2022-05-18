package user.com.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import user.com.ecommerce.model.entity.Category;
import user.com.ecommerce.model.entity.Country;
import user.com.ecommerce.model.entity.Product;
import user.com.ecommerce.model.entity.State;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

  @Autowired
  private EntityManager entityManager;

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
      CorsRegistry cors) {

    HttpMethod[] unsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

    disableHttpMethods(Product.class,config, unsupportedActions);
    disableHttpMethods(Category.class,config, unsupportedActions);
    disableHttpMethods(Country.class,config, unsupportedActions);
    disableHttpMethods(State.class,config, unsupportedActions);

    cors.addMapping("/api/**").allowedOrigins("http://localhost:4200");

    exposeIds(config);
  }

  private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] unsupportedActions) {
    config.getExposureConfiguration()
        .forDomainType(theClass)
        .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
        .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));
  }

  private void exposeIds(RepositoryRestConfiguration config) {
    Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

    List<Class> entityClasses = new ArrayList<>();

    for(EntityType type : entities){
      entityClasses.add((type.getJavaType()));
    }
    Class[] domainTypes = entityClasses.toArray(new Class[0]);
    config.exposeIdsFor(domainTypes);
  }
}
