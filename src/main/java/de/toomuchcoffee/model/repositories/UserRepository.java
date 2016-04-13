package de.toomuchcoffee.model.repositories;

import de.toomuchcoffee.model.entites.Collector;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gerald.sander on 11/04/16.
 */
public interface UserRepository extends JpaRepository<Collector, String> {

}
