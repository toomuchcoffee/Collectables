package de.toomuchcoffee.model.services;

import de.toomuchcoffee.model.entites.User;
import de.toomuchcoffee.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticatedUserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<String> getCurrentAccount() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User springSecurityUser = (User) securityContext.getAuthentication().getPrincipal();
        return Optional.ofNullable(springSecurityUser.getUsername());
    }
}
