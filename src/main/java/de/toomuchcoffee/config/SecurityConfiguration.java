package de.toomuchcoffee.config;

import de.toomuchcoffee.model.services.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    private static final PasswordEncoder PASSWORD_ENCODER = new CustomPasswordEncoder();


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
        auth
                .userDetailsService(authenticatedUserService)
                .passwordEncoder(PASSWORD_ENCODER)
                ;
    }

    private static class CustomPasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            return s != null && s.equals(charSequence.toString());
        }
    }

}
