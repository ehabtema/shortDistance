package co.za.distance.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security configuration. It permits all requests but can be modified
 * as required.
 */
@EnableWebSecurity
public class DistanceSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().
                authorizeRequests()
                .anyRequest()
                .permitAll()
                .and().csrf().disable();
    }

}
