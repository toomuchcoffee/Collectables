package de.toomuchcoffee.model.services;

import de.toomuchcoffee.model.entites.User;
import de.toomuchcoffee.model.repositories.UserRepository;
import de.toomuchcoffee.view.NewUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
