package de.toomuchcoffee.model.repositories;

import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.Ownership;
import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnershipRepository extends JpaRepository<Ownership, Long> {
    List<Ownership> findByUserAndCollectible(User user, Collectible collectible);

    List<Ownership> findByUserUsername(String username);
    List<Ownership> findByUserUsernameAndCollectibleProductLineContaining(String username, ProductLine productLine);
    List<Ownership> findByUserUsernameAndCollectibleVerbatimIgnoreCaseContaining(String username, String verbatim);
    List<Ownership> findByUserUsernameAndCollectibleProductLineContainingAndCollectibleVerbatimIgnoreCaseContaining(
            String username, ProductLine productLine, String verbatim);
}
