package de.toomuchcoffee.model.services;

import de.toomuchcoffee.model.entites.User;
import de.toomuchcoffee.model.repositories.UserRepository;
import de.toomuchcoffee.view.NewUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void create(NewUserDto newUser) {
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setRoles("USER");
        userRepository.save(user);
    }
}
