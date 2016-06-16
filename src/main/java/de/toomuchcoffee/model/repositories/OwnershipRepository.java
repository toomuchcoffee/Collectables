package de.toomuchcoffee.model.repositories;

import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.Collector;
import de.toomuchcoffee.model.entites.Ownership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnershipRepository extends JpaRepository<Ownership, Long> {
    List<Ownership> findByCollectorAndCollectible(Collector collector, Collectible collectible);
    List<Ownership> findByCollector(Collector collector);
}
