package de.toomuchcoffee.config;

import de.toomuchcoffee.model.services.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic()
          .and()
            .logout()
          .and()
            .authorizeRequests()
                .antMatchers("/index.html", "/partials/login.html", "/").permitAll()
                .antMatchers("/admin/**", "/status/**", "/swagger-ui.html").hasAuthority("ADMIN")
                .antMatchers("/**").hasAuthority("USER")
                .anyRequest().authenticated()
          .and()
          .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticatedUserService);
    }

}
