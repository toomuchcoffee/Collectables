package de.toomuchcoffee.config;

import de.toomuchcoffee.model.services.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @Override
    public Optional<String> getCurrentAuditor() {
        return authenticatedUserService.getCurrentAccount();
    }

}