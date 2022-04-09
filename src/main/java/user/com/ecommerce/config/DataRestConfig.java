package user.com.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import user.com.ecommerce.model.entity.Product;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
      CorsRegistry cors) {

    HttpMethod[] unsupportedActions = {HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE};

    config.getExposureConfiguration()
        .forDomainType(Product.class)
        .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions))
        .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions));
  }
}
