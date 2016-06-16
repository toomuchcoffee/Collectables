package de.toomuchcoffee.model.repositories;

import de.toomuchcoffee.model.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
