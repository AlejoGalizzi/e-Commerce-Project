package user.com.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class AppConfig implements WebMvcConfigurer {

  @Value("${spring.data.rest.base-path}")
  private String basePath;

  @Value("${allowed.origins}")
  private String[] allowedOrigins;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    WebMvcConfigurer.super.addCorsMappings(registry);

    registry.addMapping(basePath + "/**").allowedOrigins(allowedOrigins);
  }
}
