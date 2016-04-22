package de.toomuchcoffee.model.repositories;

import de.toomuchcoffee.model.entites.Collector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Collector, String> {
}
