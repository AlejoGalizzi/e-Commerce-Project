package user.com.ecommerce.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeRequests()
        .antMatchers("/api/orders/**")
        .authenticated()
        .and()
        .oauth2ResourceServer()
        .jwt();

    httpSecurity.cors();

    Okta.configureResourceServer401ResponseBody(httpSecurity);
  }
}
