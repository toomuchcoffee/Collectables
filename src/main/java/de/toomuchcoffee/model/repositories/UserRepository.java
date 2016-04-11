package de.toomuchcoffee.model.repositories;

import de.toomuchcoffee.model.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gerald.sander on 11/04/16.
 */
public interface UserRepository extends JpaRepository<User, String> {

}
