package de.toomuchcoffee.model.repositories;

import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.Ownership;
import de.toomuchcoffee.model.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnershipRepository extends JpaRepository<Ownership, Long> {
    List<Ownership> findByUserAndCollectible(User user, Collectible collectible);
    List<Ownership> findByUser(User user);

    List<Ownership> findByUserUsernameAndCollectibleVerbatimIgnoreCaseContaining(String username, String verbatim);

    List<Ownership> findByUserUsernameAndCollectibleProductLineAbbreviationIgnoreCaseContaining(String username, String productLine);
}
